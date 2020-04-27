/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.di.component;


import com.romellfudi.ussd.di.module.ApplicationModule;
import com.romellfudi.ussd.di.module.USSDModule;
import com.romellfudi.ussd.main.MainFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @version 1.0
 * @autor Romell Domínguez
 * @date 2020-04-20
 */

@Singleton
@Component (modules = {ApplicationModule.class, USSDModule.class})
public interface AppComponent {

    void inject(MainFragment fragment);

}
