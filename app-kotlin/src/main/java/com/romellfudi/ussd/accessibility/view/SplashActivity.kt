package com.romellfudi.ussd.accessibility.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.romellfudi.ussd.R
import com.romellfudi.ussd.accessibility.goActivity
import kotlinx.coroutines.*
import timber.log.Timber

class SplashActivity : AppCompatActivity() {

    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        supportActionBar?.hide()
//        window.setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        Timber.i("Splash Activity")

        activityScope.launch {
            delay(2000)
            goActivity<MainActivity>()
            Timber.i("starting Main Activity")
            finish()
        }
    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }
}