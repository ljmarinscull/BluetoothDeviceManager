package com.bluetoothdevicemanager;

import android.Manifest;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bluetoothdevicemanager.data.model.MyBluetoothDevice;
import com.bluetoothdevicemanager.presenter.FirstScreenPresenter;
import com.bluetoothdevicemanager.view.FirstScreenView;
import com.bluetoothdevicemanager.view.adapter.FirstScreenAdapter;

import java.util.ArrayList;
import java.util.List;

public class FirstScreenActivity extends AppCompatActivity implements FirstScreenView, OnClickListener, FirstScreenAdapter.ItemClickListener {
    private static final int REQUEST_ENABLE_BT = 20;
    private FirstScreenAdapter mAdapter;
    private BluetoothAdapter mBluetoothAdapter;
    private boolean mDoubleBackToExit;
    private List<MyBluetoothDevice> mFoundedDevice = new ArrayList<MyBluetoothDevice>();
    private ImageButton mIbRefresh;
    private ImageButton mIbSavedDevices;
    private FirstScreenPresenter mPresenter;
    private ProgressDialog mProgressDialog;
    private ProgressDialog mProgressDialogUpload;
    private RecyclerView mRecyclerView;
    private boolean isRegisterReciver = false;
    private TextView mTvListEmpty;

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                initDiscovery();
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                deviceFounded(device.getName());
                int rssiValue = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
                MyBluetoothDevice obj = new MyBluetoothDevice(device.getName(),device.getAddress(),String.valueOf(rssiValue));
                mFoundedDevice.add(obj);
                mPresenter.saveDeviceDB(obj);
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                endDiscovery();
                mAdapter.setBluetoothDevices(mFoundedDevice);
                if (mAdapter.getItemCount() == 0) {
                    mTvListEmpty.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                } else {
                    mTvListEmpty.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                }
                mAdapter.notifyDataSetChanged();
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstscreen);

        mPresenter = new FirstScreenPresenter();
        mPresenter.attachView(this);

        initView();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)  {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},10);
            } else {
                mPresenter.bluetoothSupportValidation();
            }
        } else {
            mPresenter.bluetoothSupportValidation();
        }
    }

    private void initView() {
        mIbRefresh = (ImageButton) findViewById(R.id.ibRefresh);
        mIbSavedDevices = (ImageButton) findViewById(R.id.ibSavedDevices);
        mTvListEmpty = (TextView)findViewById(R.id.tvListEmpty);
        mRecyclerView = (RecyclerView) findViewById(R.id.list);

        mIbRefresh.setOnClickListener(this);
        mIbSavedDevices.setOnClickListener(this);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getString(R.string.scanning));

        mProgressDialogUpload = new ProgressDialog(this);
        mProgressDialogUpload.setCancelable(false);
        mProgressDialogUpload.setMessage(getString(R.string.upload));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mFoundedDevice = mPresenter.getSavedDevicesDB();

        if(!mFoundedDevice.isEmpty()){
            mTvListEmpty.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }

        mAdapter = new FirstScreenAdapter();
        mAdapter.setBluetoothDevices(mFoundedDevice);
        mAdapter.setItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibRefresh:
                onSearchingDevices();
                break;
            case R.id.ibSavedDevices:
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if(!mBluetoothAdapter.isDiscovering()){
                    startActivity(new Intent(this, SecondScreenActivity.class));
                } else {
                    Toast.makeText(FirstScreenActivity.this,getResources().getString(R.string.wait_for_searching),Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void onSearchingDevices() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)  {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},10);
            } else {
                searchingDevices();
            }
        } else {
            searchingDevices();
        }
    }

    void initBroadCasts() {
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);

        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, filter);

        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        registerReceiver(mReceiver, filter);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        isRegisterReciver = true;
    }

    public void bluetoothSupport(boolean support) {
        if (support) {
            initBroadCasts();
        } else {
            showBluetoothNotSupportDialog();
        }
    }

    @Override
    public void onAddDeviceSuccess(MyBluetoothDevice obj) {
        mPresenter.removeUploadedDeviceDB(obj);
        mFoundedDevice = mPresenter.getSavedDevicesDB();
        mAdapter.setBluetoothDevices(mFoundedDevice);
        mAdapter.notifyDataSetChanged();
        Toast.makeText(this,"Dispositivo ["+obj.getName()+"] subido satisfactoriamente!",Toast.LENGTH_LONG).show();
    }

    public void onRefreshList(List<MyBluetoothDevice> list) {
        mAdapter.setBluetoothDevices(list);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onAddDeviceError() {
        Toast.makeText(this,getResources().getString(R.string.upload_device_error),Toast.LENGTH_LONG).show();
    }

    @Override
    public void initDiscovery() {
        showLoading();
        String msg = getResources().getString(R.string.init_search);
        System.out.println(msg);
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void deviceFounded(String name) {
        String msg = getResources().getString(R.string.device_founded)+name;
        System.out.println(msg);
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void endDiscovery() {
        hideLoading();
        String msg = getResources().getString(R.string.search_finished);
        System.out.println(msg);
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideUploadLoading() {
        if(mProgressDialogUpload.isShowing())
            mProgressDialogUpload.dismiss();
    }

    @Override
    public void showUploadLoading() {
        if(!mProgressDialogUpload.isShowing())
            mProgressDialogUpload.show();
    }

    void showBluetoothNotSupportDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.bluetooth_error_title)
                .setMessage(R.string.bluetooth_error_msg)
                .setPositiveButton(R.string.exit, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 10) {
            if (grantResults.length > 0 && grantResults[0] == 0 && grantResults[1] == 0) {
                initView();
            } else {
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ENABLE_BT)
        {
            if (resultCode == 0) {
                Toast.makeText(this, getResources().getString(R.string.active_bluetooth), Toast.LENGTH_LONG).show();
            } else {
                Log.i("Bluetooth",getResources().getString(R.string.bluetooth_actived));
            }
        }
    }

    private void searchingDevices() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter != null && !mBluetoothAdapter.isEnabled()) {
            startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 20);
        } else if(mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()){
            if (mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
            }
            mBluetoothAdapter.startDiscovery();
        }
    }

    @Override
    public void onBackPressed() {
        if (mDoubleBackToExit) {
            finish();
        }
        mDoubleBackToExit = true;
        Toast.makeText(this, "Presione otra vez para salir.", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDoubleBackToExit = false;
            }
        }, 2000);
    }


    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        mPresenter.terminate();
        super.onDestroy();

        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();
        }
         if(isRegisterReciver)
            unregisterReceiver(mReceiver);
    }

    @Override
    public void onItemClick(MyBluetoothDevice device) {
        mPresenter.addDevice(device);
    }

    @Override
    public void hideLoading() {
        if(mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    @Override
    public void showLoading() {
        if(!mProgressDialog.isShowing())
            mProgressDialog.show();
    }

    @Override
    public void noInternetConexion() {
        Toast.makeText(FirstScreenActivity.this,getResources().getString(R.string.not_internet),Toast.LENGTH_LONG).show();
    }
}
