package com.bluetoothdevicemanager.utils;

import com.bluetoothdevicemanager.data.model.BluetoothDeviceEntity;

import java.util.Comparator;

public class BluetoothDeviceEntityComparator implements Comparator<BluetoothDeviceEntity> {

    @Override
    public int compare(BluetoothDeviceEntity bd1, BluetoothDeviceEntity bd2) {
        return (int)Long.compare(bd1.getCreateAtLong(),bd2.getCreateAtLong());
    }
}
