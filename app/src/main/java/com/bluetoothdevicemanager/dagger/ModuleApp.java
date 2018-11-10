package com.bluetoothdevicemanager.dagger;

import com.bluetoothdevicemanager.view.FirstScreenView;

import dagger.Module;
import dagger.Provides;

@Module
public class ModuleApp {
    private final FirstScreenView mBluetoothView;

    public ModuleApp(FirstScreenView mBluetoothView) {
        this.mBluetoothView = mBluetoothView;
    }

    @Provides
    public FirstScreenView provideFirstScreenView() {
        return this.mBluetoothView;
    }
}
