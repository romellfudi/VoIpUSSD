/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.main.interactor

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.romellfudi.ussd.main.entity.Response
import javax.inject.Inject

/**
 * @autor Romell Domínguez
 * @date 2020-04-27
 * @version 1.0
 */
private val objectResponse = Response("TODO")
private val response = MutableLiveData<Response>()
        .apply { postValue(objectResponse) }

class MainFragmentInteractor @Inject constructor(private val mContext: Context) : MainFragmentMVPInteractor {

    override fun getResponse(): MutableLiveData<Response> {
        return response
    }

}