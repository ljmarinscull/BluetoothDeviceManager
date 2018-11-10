package com.bluetoothdevicemanager.data.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;

public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "bluetoothdevicemanager.db";
    private Dao<BluetoothDeviceEntity, Integer> mBluetoothDeviceEntityDao;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
        // TODO Auto-generated method stub
        try {
            TableUtils.createTable(arg1, BluetoothDeviceEntity.class);

        } catch (Exception e) {
            // TODO: handle exception
            Log.e(DBHelper.class.getName(), "Unable to create datbases", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
                          int arg3) {
        // TODO Auto-generated method stub
        try {

            TableUtils.dropTable(connectionSource, BluetoothDeviceEntity.class, true);
            onCreate(arg0, connectionSource);

        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(DBHelper.class.getName(), "Unable to upgrade database from version " + arg1 + " to new "
                    + arg2, e);
        }
    }

    public Dao<BluetoothDeviceEntity, Integer> getBluetoothDeviceEntityDao() throws Exception {
        if (mBluetoothDeviceEntityDao == null) {
            mBluetoothDeviceEntityDao = getDao(BluetoothDeviceEntity.class);
        }
        return mBluetoothDeviceEntityDao;
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
        super.close();
        mBluetoothDeviceEntityDao = null;
    }
}
