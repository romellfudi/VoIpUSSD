/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.accessibility

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.romellfudi.ussd.R

//import com.rbddevs.splashy.Splashy

/**
 * Extensions
 *
 * @version 1.0.a
 * @autor Romell Domínguez (@romellfudi)
 * @date 3/21/21
 */
//fun Splashy.complete(postDelay:() -> Unit){
//    show()
//    Splashy.onComplete(object : Splashy.OnComplete {
//        override fun onComplete() {
//            postDelay()
//        }
//    })
//}
@RequiresApi(Build.VERSION_CODES.N)
inline fun <reified T : Activity> Activity.goActivity(extras: HashMap<String, String>? = null) {
    intent = Intent(this, T::class.java).apply {
        extras?.forEach { key, value -> putExtra(key, value) }
    }
    startActivity(intent)
    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
}