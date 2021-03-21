/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.accessibility

import com.rbddevs.splashy.Splashy

/**
 * Extensions
 *
 * @version 1.0.a
 * @autor Romell Domínguez (@romellfudi)
 * @date 3/21/21
 */
fun Splashy.complete(postDelay:() -> Unit){
    show()
    Splashy.onComplete(object : Splashy.OnComplete {
        override fun onComplete() {
            postDelay()
        }
    })
}