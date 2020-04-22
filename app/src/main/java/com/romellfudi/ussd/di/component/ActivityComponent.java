/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.di.component;

import com.romellfudi.ussd.di.ActivityScope;
import com.romellfudi.ussd.di.module.ActivityModule;
import com.romellfudi.ussd.sample.MainActivity;

import dagger.Component;

/**
 * @version 1.0
 * @autor Romell Domínguez
 * @date 2020-04-20
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
//    void inject(MainFragment mainFragment);

}
