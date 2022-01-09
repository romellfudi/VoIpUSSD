/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.main

import android.app.Activity
import android.app.Service
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment

/**
 * Extensions
 *
 * @version 1.0.a
 * @autor Romell Domínguez (@romellfudi)
 * @date 3/21/21
 */
object service {
    var internalIntent:Intent? = null
}

@RequiresApi(Build.VERSION_CODES.N)
inline fun <reified T : Service> Activity.goService(extras:HashMap<String,String>?=null ) {
    service.internalIntent = Intent(this, T::class.java).apply {
        extras?.forEach(::putExtra) // key, value -> putExtra(key, value)
    }
    startService(service.internalIntent)
}

inline fun <reified T : Service> Fragment.goService(extras:HashMap<String,String>?=null ) {
    activity!!.goService<T>(extras)
}

fun Activity.dismissIntent(){
    service.internalIntent?.let { stopService(it) }
    service.internalIntent = null
}
fun Fragment.dismissIntent(){
    activity!!.dismissIntent()
}
