/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.sample

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.romellfudi.ussd.R

/**
 *
 * @author Romell Domínguez
 * @version 1.0.0 12/12/2018
 * @since 1.1
 */
class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        val me = this
        val mIntent = intent
        val time = mIntent.getIntExtra("time", 3000)

        Handler().postDelayed({
            startActivity(Intent(me, MainActivity::class.java))
            finish()
        }, time.toLong())
    }
}
