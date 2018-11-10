package com.bluetoothdevicemanager.data.api.client;

import com.bluetoothdevicemanager.data.api.retrofit.BluetoothDeviceRetrofitClient;
import com.bluetoothdevicemanager.data.model.MyBluetoothDevice;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class BluetoothDeviceClient extends BluetoothDeviceRetrofitClient implements BluetoothDeviceService {
    public Observable<List<MyBluetoothDevice>> getDevices() {
        return getBluetoothDeviceService().getDevices().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<MyBluetoothDevice> addDevice(MyBluetoothDevice device) {
        return getBluetoothDeviceService().addDevice(device).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
