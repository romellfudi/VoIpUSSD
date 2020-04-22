/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd;

import android.app.Application;

import com.romellfudi.ussd.di.component.AppComponent;
import com.romellfudi.ussd.di.component.DaggerAppComponent;
import com.romellfudi.ussd.di.module.USSDModule;

/**
 * @version 1.0
 * @autor Romell Domínguez
 * @date 2020-04-21
 */
public class App extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
//                .applicationModule(new ApplicationModule(this))
                .uSSDModule(new USSDModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
