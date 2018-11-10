package com.bluetoothdevicemanager.dagger;

import com.bluetoothdevicemanager.FirstScreenActivity;
import dagger.Component;

@Component(modules = {ModuleApp.class})
public interface ComponentApp {
    void inject(FirstScreenActivity firstScreenActivity);
}
