package com.bluetoothdevicemanager.view;

import com.bluetoothdevicemanager.data.model.BluetoothDeviceEntity;
import com.bluetoothdevicemanager.view.base.BaseView;
import java.util.List;

public interface SecondScreenView extends BaseView {
    void onRefreshList(List<BluetoothDeviceEntity> list);
    void onGetDeviceError();
}
