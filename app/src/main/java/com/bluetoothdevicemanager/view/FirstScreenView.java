package com.bluetoothdevicemanager.view;

import com.bluetoothdevicemanager.data.model.MyBluetoothDevice;
import com.bluetoothdevicemanager.view.base.BaseView;
import java.util.List;

public interface FirstScreenView extends BaseView {
    void bluetoothSupport(boolean result);

    void onAddDeviceSuccess(MyBluetoothDevice device);

    void onAddDeviceError();

    void initDiscovery();

    void deviceFounded(String name);

    void endDiscovery();

    void hideUploadLoading();

    void showUploadLoading();
}
