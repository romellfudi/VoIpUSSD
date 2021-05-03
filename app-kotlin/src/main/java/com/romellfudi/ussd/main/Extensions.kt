/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.main

import android.app.Activity
import android.app.Service
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.rbddevs.splashy.Splashy

/**
 * Extensions
 *
 * @version 1.0.a
 * @autor Romell Domínguez (@romellfudi)
 * @date 3/21/21
 */
object service {
    var intent:Intent? = null
}
@RequiresApi(Build.VERSION_CODES.N)
inline fun <reified T : Service> Activity.goService(extras:HashMap<String,String>?=null ) {
    service.intent = Intent(this, T::class.java)
    extras?.forEach { key, value -> intent.putExtra(key,value) }
    startService(intent)
}

inline fun <reified T : Service> Fragment.goService(extras:HashMap<String,String>?=null ) {
    activity?.goService<T>(extras)
}

fun Activity.dismissIntent(){
    service.intent?.let { stopService(service.intent) }
    service.intent = null
}
fun Fragment.dismissIntent(){
    activity?.dismissIntent()
}
