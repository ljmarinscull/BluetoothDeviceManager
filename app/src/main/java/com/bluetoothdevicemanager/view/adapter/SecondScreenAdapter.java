package com.bluetoothdevicemanager.view.adapter;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluetoothdevicemanager.R;
import com.bluetoothdevicemanager.data.model.BluetoothDeviceEntity;
import com.bluetoothdevicemanager.data.model.MyBluetoothDevice;
import java.util.Collections;
import java.util.List;

public class SecondScreenAdapter extends Adapter<SecondScreenAdapter.SecondScreenViewHolder> {
    private List<BluetoothDeviceEntity> mSavedDeviceList = Collections.emptyList();

    public static class SecondScreenViewHolder extends ViewHolder {
        BluetoothDeviceEntity mDevice;
        TextView mTvCreateAt;
        TextView mTvName;
        TextView mTvStrength;
        View mView;

        public SecondScreenViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvName.setSelected(true);
            mTvStrength = (TextView) itemView.findViewById(R.id.tvStrength);
            mTvCreateAt = (TextView) itemView.findViewById(R.id.tvCreateAt);
        }
    }

    public SecondScreenViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SecondScreenViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_device_item, parent, false));
    }

    public void onBindViewHolder(SecondScreenViewHolder holder, int position) {
        BluetoothDeviceEntity device = (BluetoothDeviceEntity) this.mSavedDeviceList.get(position);
        holder.mDevice = device;
        holder.mTvName.setText(device.getName());
        holder.mTvStrength.setText(device.getStrength());
        holder.mTvCreateAt.setText(device.getCreateAt());
    }

    public int getItemCount() {
        return this.mSavedDeviceList.size();
    }

    public void setBluetoothDevices(List<BluetoothDeviceEntity> myBluetoothDevices) {
        this.mSavedDeviceList = myBluetoothDevices;
    }
}
