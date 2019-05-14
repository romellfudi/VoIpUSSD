package com.romellfudi.ussd.sample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity

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
            val intent = Intent(me, MainActivity::class.java)
            intent.putExtra("stop", true)
            startActivity(intent)
            finish()
        }, time.toLong())
    }
}
