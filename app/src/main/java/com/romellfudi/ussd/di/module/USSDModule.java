/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.di.module;

import android.content.Context;

import com.github.javafaker.Faker;
import com.romellfudi.ussdlibrary.USSDApi;
import com.romellfudi.ussdlibrary.USSDController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @version 1.0
 * @autor Romell Domínguez
 * @date 2020-04-20
 */
@Module
public class USSDModule {

    private Context context;

    public USSDModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public USSDApi provideUSSDApi() {
        return USSDController.getInstance(context);
    }

    @Provides
    @Singleton
    public HashMap<String, HashSet<String>> provideHashMap() {
        HashMap map = new HashMap<>();
        map.put("KEY_LOGIN", new HashSet<>(Arrays.asList("espere", "waiting", "loading", "esperando")));
        map.put("KEY_ERROR", new HashSet<>(Arrays.asList("problema", "problem", "error", "null")));
        return map;
    }

    @Provides
    @Singleton
    public Faker provideFaker(){
        return new Faker(Locale.ENGLISH);
    }

    @Provides
    public String[] provideInfo(){
        String simSlotName[] = {
                "extra_asus_dial_use_dualsim",
                "com.android.phone.extra.slot",
                "slot",
                "simslot",
                "sim_slot",
                "subscription",
                "Subscription",
                "phone",
                "com.android.phone.DialingMode",
                "simSlot",
                "slot_id",
                "simId",
                "simnum",
                "phone_type",
                "slotId",
                "slotIdx"
        };
        return simSlotName;
    }


}
