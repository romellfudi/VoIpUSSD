/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porftolio.romellfudi.com
 */

package com.romellfudi.ussd.main;

/**
 * Dao Object
 *
 * @author Romell Domínguez
 * @version 1.1.c 8/25/20
 * @since 1.0.a
 */
public class Dao {
    private String phoneNumber;
    private String result;

    public Dao() {
        phoneNumber = "*515#";
        result = "Nothing is incoming";
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
