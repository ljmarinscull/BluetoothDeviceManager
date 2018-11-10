package com.bluetoothdevicemanager.view.adapter;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bluetoothdevicemanager.R;
import com.bluetoothdevicemanager.data.model.MyBluetoothDevice;
import java.util.ArrayList;
import java.util.List;

public class FirstScreenAdapter extends Adapter<FirstScreenAdapter.FirstScreenViewHolder> {

    private ItemClickListener mItemClickListener;
    private List<MyBluetoothDevice> mMyBluetoothDeviceList;

    public interface ItemClickListener {
        void onItemClick(MyBluetoothDevice myBluetoothDevice);
    }

    public static class FirstScreenViewHolder extends ViewHolder {
        MyBluetoothDevice mDevice;
        ImageButton mIbUpload;
        TextView mTvName;
        TextView mTvStrength;
        View mView;

        public FirstScreenViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvName.setSelected(true);
            mTvStrength = (TextView) itemView.findViewById(R.id.tvStrength);
            mIbUpload = (ImageButton) itemView.findViewById(R.id.ibUpload);
        }
    }

    public FirstScreenAdapter() {
        this.mMyBluetoothDeviceList = new ArrayList<MyBluetoothDevice>();
    }

    public FirstScreenViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FirstScreenViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bluetooth_device_item, parent, false));
    }

    public void onBindViewHolder(FirstScreenViewHolder holder, int position) {
        MyBluetoothDevice device = (MyBluetoothDevice) mMyBluetoothDeviceList.get(position);
        holder.mDevice = device;
        holder.mTvName.setText(device.getName());
        holder.mTvStrength.setText(device.getStrengthStr());
        holder.mIbUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(device);
                }
            }
        });
    }

    public int getItemCount() {
        return mMyBluetoothDeviceList.size();
    }

    public void setBluetoothDevices(List<MyBluetoothDevice> myBluetoothDevices) {
        mMyBluetoothDeviceList = myBluetoothDevices;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}
