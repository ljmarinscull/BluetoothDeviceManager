package com.bluetoothdevicemanager.data.model.mapper;

import com.bluetoothdevicemanager.data.model.BluetoothDeviceEntity;
import com.bluetoothdevicemanager.data.model.MyBluetoothDevice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BluetoothDeviceMapper extends Mapper<MyBluetoothDevice,BluetoothDeviceEntity> {

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public BluetoothDeviceMapper() {

    }

    @Override
    public BluetoothDeviceEntity map(MyBluetoothDevice value) {
        BluetoothDeviceEntity entity = null;
        if(value != null){
            entity = new BluetoothDeviceEntity();
            entity.setId(value.getId());
            entity.setName(value.getName());
            entity.setAddress(value.getAddress());
            entity.setStrength(value.getStrengthStr());
            entity.setCreateAt(value.getCreateAt());
            try {
                Date date = format.parse(value.getCreateAt());
                long create_at_long = date.getTime();
                entity.setCreateAtLong(create_at_long);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        return entity;
    }

    @Override
    public MyBluetoothDevice reverseMap(BluetoothDeviceEntity value) {
        MyBluetoothDevice device = null;
        if (value != null){
            device = new MyBluetoothDevice();
            device.setId(value.getId());
            device.setName(value.getName());
            device.setAddress(value.getAddress());
            device.setStrength(value.getStrength());
            device.setCreateAt(value.getCreateAt());
        }
        return device;
    }

    @Override
    public List<BluetoothDeviceEntity> listMap(List<MyBluetoothDevice> value) {

        List<BluetoothDeviceEntity> result = new ArrayList<BluetoothDeviceEntity>();
        if(value != null){
            for(MyBluetoothDevice obj: value){
                result.add(map(obj));
            }
        }

        return result;
    }

    @Override
    public List<MyBluetoothDevice> reverseListMap(List<BluetoothDeviceEntity> value) {
        List<MyBluetoothDevice> result = new ArrayList<MyBluetoothDevice>();
        if(value != null){
            for(BluetoothDeviceEntity obj: value){
                result.add(reverseMap(obj));
            }
        }

        return result;
    }
}
