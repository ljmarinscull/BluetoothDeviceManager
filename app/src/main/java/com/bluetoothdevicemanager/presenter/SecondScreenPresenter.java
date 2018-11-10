package com.bluetoothdevicemanager.presenter;

import com.bluetoothdevicemanager.SecondScreenActivity;
import com.bluetoothdevicemanager.data.api.client.BluetoothDeviceClient;
import com.bluetoothdevicemanager.data.model.BluetoothDeviceEntity;
import com.bluetoothdevicemanager.data.model.MyBluetoothDevice;
import com.bluetoothdevicemanager.data.model.mapper.BluetoothDeviceMapper;
import com.bluetoothdevicemanager.presenter.base.BasePresenter;
import com.bluetoothdevicemanager.utils.BluetoothDeviceEntityComparator;
import com.bluetoothdevicemanager.utils.NetworkUtils;
import com.bluetoothdevicemanager.view.SecondScreenView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class SecondScreenPresenter extends BasePresenter<SecondScreenView> {
    public BluetoothDeviceClient mBluetoothDeviceClient;
    private List<BluetoothDeviceEntity> mBluetoothDeviceEntityList;

    public SecondScreenPresenter() {
        mBluetoothDeviceClient = new BluetoothDeviceClient();
    }

    public void getSavedDevices() {

        if(NetworkUtils.isNetworkConnected((SecondScreenActivity)getView())){
            getView().showLoading();
            Disposable disposable = mBluetoothDeviceClient.getDevices()
                    .subscribe(obj -> {
                        getView().hideLoading();
                        if (obj != null ) {
                            getView().onRefreshList(mappingObjs(obj));
                        } else {
                            getView().onGetDeviceError();
                        }
                    },Throwable -> {
                        getView().hideLoading();
                        getView().onGetDeviceError();
                        Throwable.printStackTrace();
                    });

            addDisposableObserver(disposable);
        } else {
            getView().noInternetConexion();
        }
    }

    private List<BluetoothDeviceEntity> mappingObjs(List<MyBluetoothDevice> list) {
        List<BluetoothDeviceEntity> resultList = new ArrayList<BluetoothDeviceEntity>();
        BluetoothDeviceMapper mapper = new BluetoothDeviceMapper();
        for (int i = 0; i < list.size(); i++ ){
            MyBluetoothDevice obj = list.get(i);
            resultList.add(mapper.map(obj));
        }
        mBluetoothDeviceEntityList = resultList;
        return resultList;
    }

    public void refresListByOrder(int order) {
        if(order == 0){
            Collections.sort(mBluetoothDeviceEntityList, new BluetoothDeviceEntityComparator());
        } else {
            Collections.reverse(mBluetoothDeviceEntityList);
        }
        getView().onRefreshList(mBluetoothDeviceEntityList);
    }
}
