package com.bluetoothdevicemanager.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static com.bluetoothdevicemanager.data.api.Constants.ADDRESS;
import static com.bluetoothdevicemanager.data.api.Constants.CREATEAT;
import static com.bluetoothdevicemanager.data.api.Constants.ID;
import static com.bluetoothdevicemanager.data.api.Constants.NAME;
import static com.bluetoothdevicemanager.data.api.Constants.STRENGTH;
import static com.bluetoothdevicemanager.data.api.Constants.UPLOADED;

@DatabaseTable(tableName = "dat_bluetooth_device")
public class BluetoothDeviceEntity implements Serializable, Parcelable  {

    @DatabaseField(canBeNull = false, columnName = ADDRESS, unique = true)
    private String address;
    @DatabaseField(canBeNull = false, columnName = CREATEAT)
    private String created_at;
    @DatabaseField(canBeNull = false, columnName = ID)
    private String id;
    @DatabaseField(canBeNull = false, columnName = NAME)
    private String name;
    @DatabaseField(canBeNull = false, columnName = STRENGTH)
    private String strength;
    @DatabaseField(canBeNull = false, columnName = UPLOADED)
    private boolean uploaded;

    private long create_at_long;

    public BluetoothDeviceEntity() {
        this.id = "unknow";
        this.name = "unknow";
        this.address = "unknow";
        this.strength = "unknow";
        Date now = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String nowStr = formatter.format(now);
        this.created_at = nowStr;
        this.created_at = "unknow";
        this.uploaded = false;
        this.create_at_long = now.getTime();
    }

    public BluetoothDeviceEntity(String id,String name,String address,String strength,String create_at,boolean uploaded,long create_at_long) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.strength = strength;
        this.created_at = create_at;
        this.uploaded = uploaded;
        this.create_at_long = create_at_long;
    }


    protected BluetoothDeviceEntity(Parcel in) {
        address = in.readString();
        created_at = in.readString();
        id = in.readString();
        name = in.readString();
        strength = in.readString();
        uploaded = in.readByte() != 0;
        create_at_long = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(created_at);
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(strength);
        dest.writeByte((byte) (uploaded ? 1 : 0));
        dest.writeLong(create_at_long);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BluetoothDeviceEntity> CREATOR = new Creator<BluetoothDeviceEntity>() {
        @Override
        public BluetoothDeviceEntity createFromParcel(Parcel in) {
            return new BluetoothDeviceEntity(in);
        }

        @Override
        public BluetoothDeviceEntity[] newArray(int size) {
            return new BluetoothDeviceEntity[size];
        }
    };

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

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getCreateAt() {
        return this.created_at;
    }

    public void setCreateAt(String create_at) {
        this.created_at = create_at;
    }

    public boolean isUploaded() {
        return this.uploaded;
    }

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }

    public long getCreateAtLong() {
        return create_at_long;
    }

    public void setCreateAtLong(long create_at_long) {
        this.create_at_long = create_at_long;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BluetoothDeviceEntity that = (BluetoothDeviceEntity) o;
        return address.equals(that.address);
    }

    @Override
    public int hashCode() {
        return address.hashCode();
    }
}
