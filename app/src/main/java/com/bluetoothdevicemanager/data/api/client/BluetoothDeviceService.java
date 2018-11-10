package com.bluetoothdevicemanager.data.api.client;

import com.bluetoothdevicemanager.data.model.MyBluetoothDevice;
import io.reactivex.Observable;
import java.util.List;

public interface BluetoothDeviceService {
    Observable<MyBluetoothDevice> addDevice(MyBluetoothDevice myBluetoothDevice);

    Observable<List<MyBluetoothDevice>> getDevices();
}
