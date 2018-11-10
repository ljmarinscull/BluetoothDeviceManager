package com.bluetoothdevicemanager.presenter;

import android.bluetooth.BluetoothAdapter;
import com.bluetoothdevicemanager.FirstScreenActivity;
import com.bluetoothdevicemanager.data.api.client.BluetoothDeviceClient;
import com.bluetoothdevicemanager.data.model.BluetoothDeviceEntity;
import com.bluetoothdevicemanager.data.model.DBHelper;
import com.bluetoothdevicemanager.data.model.MyBluetoothDevice;
import com.bluetoothdevicemanager.data.model.mapper.BluetoothDeviceMapper;
import com.bluetoothdevicemanager.presenter.base.BasePresenter;
import com.bluetoothdevicemanager.utils.NetworkUtils;
import com.bluetoothdevicemanager.view.FirstScreenView;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import static com.bluetoothdevicemanager.data.api.Constants.ADDRESS;
import static com.bluetoothdevicemanager.data.api.Constants.UPLOADED;

public class FirstScreenPresenter extends BasePresenter<FirstScreenView> {

    private Dao<BluetoothDeviceEntity, Integer> mBluetoothDeviceEntityDao;
    public BluetoothDeviceClient mBluetoothDeviceClient;
    private BluetoothAdapter mBluetoothAdapter;
    private DBHelper mDBHelper;
    private BluetoothDeviceMapper mapper = new BluetoothDeviceMapper();

    public FirstScreenPresenter() {
        mBluetoothDeviceClient = new BluetoothDeviceClient();
    }

    private DBHelper getHelper() {
        if (mDBHelper == null) {
            mDBHelper = (DBHelper) OpenHelperManager.getHelper((FirstScreenActivity) getView(), DBHelper.class);
        }
        return mDBHelper;
    }

    public void bluetoothSupportValidation() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            getView().bluetoothSupport(false);
        } else {
            getView().bluetoothSupport(true);
        }
    }

    public void addDevice(final MyBluetoothDevice device) {

        if(NetworkUtils.isNetworkConnected((FirstScreenActivity)getView())){
            getView().showUploadLoading();
            Disposable disposable = mBluetoothDeviceClient.addDevice(device)
                    .subscribe(obj -> {
                        getView().hideUploadLoading();
                        if (obj != null) {
                            getView().onAddDeviceSuccess(obj);
                        }else{
                            getView().onAddDeviceError();
                        }
                    }, Throwable ->{
                        getView().hideUploadLoading();
                        getView().onAddDeviceError();});

            addDisposableObserver(disposable);
        } else {
            getView().noInternetConexion();
        }
    }

    public List<MyBluetoothDevice> getSavedDevicesDB(){
        try {
            mBluetoothDeviceEntityDao = getHelper().getBluetoothDeviceEntityDao();
            QueryBuilder<BluetoothDeviceEntity, Integer> queryBuilder =
                    mBluetoothDeviceEntityDao.queryBuilder();
            Where<BluetoothDeviceEntity, Integer> where = queryBuilder.where();
            where.eq(UPLOADED,false);
            PreparedQuery<BluetoothDeviceEntity> preparedQuery = queryBuilder.prepare();
            List<BluetoothDeviceEntity> objs = mBluetoothDeviceEntityDao.query(preparedQuery);
            return mapper.reverseListMap(objs);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<MyBluetoothDevice>();
    }

    public void saveDeviceDB(MyBluetoothDevice device){
        try {
            QueryBuilder<BluetoothDeviceEntity, Integer> queryBuilder =
                    mBluetoothDeviceEntityDao.queryBuilder();
            Where<BluetoothDeviceEntity, Integer> where = queryBuilder.where();
            where.eq(ADDRESS,  device.getAddress());
            PreparedQuery<BluetoothDeviceEntity> preparedQuery = queryBuilder.prepare();
            List<BluetoothDeviceEntity> objs = mBluetoothDeviceEntityDao.query(preparedQuery);
            if(objs != null && objs.isEmpty()){
                mBluetoothDeviceEntityDao.create(mapper.map(device));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void removeUploadedDeviceDB(MyBluetoothDevice device){
        try {
            DeleteBuilder<BluetoothDeviceEntity, Integer> deleteBuilder =
                    mBluetoothDeviceEntityDao.deleteBuilder();
            deleteBuilder.where().eq(ADDRESS,device.getAddress());
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
