package com.romellfudi.ussd;

import android.app.Application;

import com.romellfudi.ussd.di.AppComponent;
import com.romellfudi.ussd.di.DaggerAppComponent;
import com.romellfudi.ussd.di.USSDModule;

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
                .uSSDModule(new USSDModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
