package com.romellfudi.ussd.accessibility.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.romellfudi.ussd.R
import com.romellfudi.ussd.accessibility.goActivity
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {

    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        activityScope.launch {
            delay(3000)
            goActivity<MainActivity>()
            finish()
        }
    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }
}