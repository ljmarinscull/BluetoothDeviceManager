package com.bluetoothdevicemanager.data.api.retrofit;

import com.bluetoothdevicemanager.data.model.MyBluetoothDevice;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BluetoothDeviceRetrofitService {
    @POST("/add")
    Observable<MyBluetoothDevice> addDevice(@Body MyBluetoothDevice myBluetoothDevice);

    @GET("/devices/")
    Observable<List<MyBluetoothDevice>> getDevices();
}
