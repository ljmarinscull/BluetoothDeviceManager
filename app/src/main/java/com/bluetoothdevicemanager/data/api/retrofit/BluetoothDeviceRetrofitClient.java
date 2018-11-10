package com.bluetoothdevicemanager.data.api.retrofit;

import com.bluetoothdevicemanager.data.api.Constants;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BluetoothDeviceRetrofitClient {
    private BluetoothDeviceRetrofitService bluetoothDeviceRetrofitService;

    public BluetoothDeviceRetrofitClient() {
        initRetrofit();
    }

    private void initRetrofit() {
        this.bluetoothDeviceRetrofitService = (BluetoothDeviceRetrofitService) retrofitBuilder().create(getSpotifyServiceClass());
    }

    private Retrofit retrofitBuilder() {
        return new Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create(getSpotifyDeserializer())).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
    }

    private Class<BluetoothDeviceRetrofitService> getSpotifyServiceClass() {
        return BluetoothDeviceRetrofitService.class;
    }

    private Gson getSpotifyDeserializer() {
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setLenient().create();
    }

    protected BluetoothDeviceRetrofitService getBluetoothDeviceService() {
        return this.bluetoothDeviceRetrofitService;
    }
}
