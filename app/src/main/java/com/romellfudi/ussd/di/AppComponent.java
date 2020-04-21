package com.romellfudi.ussd.di;


import com.romellfudi.ussd.sample.MainActivity;
import com.romellfudi.ussd.sample.MainFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @version 1.0
 * @autor Romell Domínguez
 * @date 2020-04-20
 */

@Singleton
@Component(modules = {USSDModule.class})
public interface AppComponent {

    void inject(MainFragment fragment);
    void inject(MainActivity activity);
}
