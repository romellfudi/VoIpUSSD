/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.accessibility.view

/**
 * @version 1.0
 * @autor Romell Domínguez
 * @date 2020-04-26
 */
interface MainMVPView {
    fun checkUpdate()
    fun showMessage(message: String)
    fun notifyUser()
}
