/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.main.interactor

import androidx.lifecycle.MutableLiveData
import com.romellfudi.ussd.main.entity.Response
import io.reactivex.Observable


/**
 * @autor Romell Domínguez
 * @date 2020-04-27
 * @version 1.0
 */
interface MainFragmentMVPInteractor {
    fun getResponse(): MutableLiveData<Response>
}