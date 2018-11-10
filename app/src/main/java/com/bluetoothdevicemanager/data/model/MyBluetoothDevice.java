package com.bluetoothdevicemanager.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.Time;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.bluetoothdevicemanager.data.api.Constants.ADDRESS;
import static com.bluetoothdevicemanager.data.api.Constants.CREATEAT;
import static com.bluetoothdevicemanager.data.api.Constants.DBM;
import static com.bluetoothdevicemanager.data.api.Constants.ID;
import static com.bluetoothdevicemanager.data.api.Constants.NAME;
import static com.bluetoothdevicemanager.data.api.Constants.STRENGTH;

public class MyBluetoothDevice implements Parcelable {

    @SerializedName(ADDRESS)
    private String address;

    @SerializedName(CREATEAT)
    private String created_at;

    @SerializedName(ID)
    private String id;

    @SerializedName(NAME)
    private String name;

    @SerializedName(STRENGTH)
    private String strength;

    public MyBluetoothDevice(){}
    public MyBluetoothDevice(String name, String address, String strength) {
        this.id = "unknow";
        this.name = name;
        this.address = address;
        this.strength = strength;
        Date now = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String nowStr = formatter.format(now);
        this.created_at = nowStr;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStrength() {
        return this.strength;
    }

    public String getStrengthStr() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.strength);
        stringBuilder.append(DBM);
        return stringBuilder.toString();
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getCreateAt() {
        return this.created_at;
    }

    public void setCreateAt(String create_at) {
        this.created_at = create_at;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address);
        dest.writeString(this.created_at);
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.strength);
    }

    protected MyBluetoothDevice(Parcel in) {
        this.address = in.readString();
        this.created_at = in.readString();
        this.id = in.readString();
        this.name = in.readString();
        this.strength = in.readString();
    }

    public static final Creator<MyBluetoothDevice> CREATOR = new Creator<MyBluetoothDevice>() {
        @Override
        public MyBluetoothDevice createFromParcel(Parcel source) {
            return new MyBluetoothDevice(source);
        }

        @Override
        public MyBluetoothDevice[] newArray(int size) {
            return new MyBluetoothDevice[size];
        }
    };
}
