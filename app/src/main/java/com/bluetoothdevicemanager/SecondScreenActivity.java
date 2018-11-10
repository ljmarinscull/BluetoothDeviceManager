package com.bluetoothdevicemanager;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bluetoothdevicemanager.data.model.BluetoothDeviceEntity;
import com.bluetoothdevicemanager.data.model.MyBluetoothDevice;
import com.bluetoothdevicemanager.presenter.SecondScreenPresenter;
import com.bluetoothdevicemanager.view.SecondScreenView;
import com.bluetoothdevicemanager.view.adapter.SecondScreenAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class SecondScreenActivity extends AppCompatActivity implements SecondScreenView {
    private SecondScreenAdapter mAdapter;
    SecondScreenPresenter mPresenter;
    private RecyclerView mRecyclerView;
    private TextView mTvListEmpty;
    private ProgressDialog mProgressDialog;
    private Menu mMenu;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_secondscreen);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();

        mPresenter = new SecondScreenPresenter();
        mPresenter.attachView(this);
      //  mPresenter.getSavedDevices();
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        mPresenter.terminate();
        super.onDestroy();
    }

    private void initView() {
        mTvListEmpty = findViewById(R.id.tvListEmpty);
        mRecyclerView = (RecyclerView) findViewById(R.id.list);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getString(R.string.download));

        mAdapter = new SecondScreenAdapter();

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        mRecyclerView.setAdapter(mAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        mMenu = menu;
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            finish();
        } else if (itemId == R.id.action_order) {
            showOrderTypeDialog();
        } else if (itemId == R.id.action_update) {
            mMenu.findItem(R.id.action_update).setEnabled(false);
            mPresenter.getSavedDevices();
        }
        return true;
    }

    public void onBackPressed() {
        finish();
    }

    public void showOrderTypeDialog() {
        Builder builder = new Builder(this);
        builder.setTitle(R.string.order_type)
                .setSingleChoiceItems(new CharSequence[]{"Ascendente", "Descendente"}, 0, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.refresListByOrder(which);
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    public void onRefreshList(List<BluetoothDeviceEntity> list) {
        mAdapter.setBluetoothDevices(list);
        if (mAdapter.getItemCount() == 0) {
            mTvListEmpty.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            mMenu.findItem(R.id.action_order).setEnabled(false);
        } else {
            mTvListEmpty.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mMenu.findItem(R.id.action_order).setEnabled(true);
        }
        mMenu.findItem(R.id.action_update).setEnabled(true);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetDeviceError() {
        mMenu.findItem(R.id.action_order).setEnabled(false);
        mMenu.findItem(R.id.action_update).setEnabled(true);
        Toast.makeText(SecondScreenActivity.this,getResources().getString(R.string.get_devices_error),Toast.LENGTH_LONG).show();
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
        mMenu.findItem(R.id.action_update).setEnabled(true);
        Toast.makeText(SecondScreenActivity.this,getResources().getString(R.string.not_internet),Toast.LENGTH_LONG).show();
    }
}
