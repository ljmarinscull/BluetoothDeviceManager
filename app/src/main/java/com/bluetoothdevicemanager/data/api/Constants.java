package com.bluetoothdevicemanager.data.api;

public class Constants {
    public static final String BASE_URL = "https://grin-bluetooth-api.herokuapp.com";
    public static final String NOT_UPLOADED_DEVICES = "NOT_UPLOADED_DEVICES";

    public static final class Endpoint {
        public static final String ADD_DEVICE = "/add";
        public static final String GET_DEVICES = "/devices/";
    }

    public static final class Params {
        public static final String DEVICE = "device";
    }

    public static final String ADDRESS = "address";
    public static final String CREATEAT = "created_at";
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String STRENGTH = "strength";
    public static final String UPLOADED = "uploaded";
    public static final String DBM = "dBm";
}
