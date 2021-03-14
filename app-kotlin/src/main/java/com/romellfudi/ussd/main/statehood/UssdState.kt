/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.main.statehood

/**
 * @version 1.0
 * @autor Romell Domínguez
 * @date 2021-03-14
 */
sealed class UssdState {
    object Successful : UssdState()
    data class Progress(val progress: Int) : UssdState()
    data class Error(val errorMessage: String = "Not complete the whole path") : UssdState()
}