/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Practical ViewModel
 *
 * @author Romell Domínguez
 * @version 1.1.c 8/25/20
 * @since 1.0.a
 */
public class DaoViewModel extends ViewModel {

    private MutableLiveData<Dao> daoMutableLiveData;

    public MutableLiveData<String> phoneNumber = new MutableLiveData<>();
    public MutableLiveData<String> result = new MutableLiveData<>();

    public DaoViewModel() {
        Dao dao = new Dao();
        phoneNumber.setValue(dao.getPhoneNumber());
        result.setValue(dao.getResult());
    }

    public LiveData<Dao> getDao() {
        if (daoMutableLiveData == null) {
            daoMutableLiveData = new MutableLiveData<>();
        }
        return daoMutableLiveData;
    }

}
