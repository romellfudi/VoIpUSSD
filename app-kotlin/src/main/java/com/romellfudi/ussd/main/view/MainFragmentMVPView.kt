/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.main.view

import com.romellfudi.ussd.main.statehood.UssdState
import com.romellfudi.ussdlibrary.USSDApi

/**
 * @version 1.0
 * @autor Romell Domínguez
 * @date 2020-04-26
 */
interface MainFragmentMVPView {
    val ussdNumber: String
    val ussdApi: USSDApi
    val hasAllowOverlay: Boolean
    fun showOverlay()
    fun showSplashOverlay()
    fun showResult(result: String)
    fun observeUssdState(result: UssdState)
    fun dismissOverlay()
    fun dialUp()
}
