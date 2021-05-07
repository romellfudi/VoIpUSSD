/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

/**
 * BoostTag E.I.R.L. All Copyright Reserved
 * www.boosttag.com
 */
package com.romellfudi.ussd.main.view

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.graphics.Point
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.TypedValue
import android.view.Gravity
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.RenderMode
import com.romellfudi.ussd.R
import kotlin.math.roundToInt

/**
 * SplashLoadingService for Android splashing dialog
 *
 * @author Romell Dominguez
 * @version 1.1.i 2019/04/18
 * @since 1.1.i
 */
class CustomSplashService : Service() {

    private var layout: LinearLayout? = null
    private lateinit var wm: WindowManager

    private var message: String? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    @SuppressLint("ResourceAsColor")
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (intent.hasExtra("EXTRA"))
            message = intent.getStringExtra("EXTRA")
        wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val size = Point()
        wm.defaultDisplay.getSize(size)

        val paddingInDp = 100
        val scale = resources.displayMetrics.density
        val paddingInPx = (paddingInDp * scale + 0.5f).toInt()

        val lottie = LottieAnimationView(this).apply {
            setAnimation(R.raw.ussd_interface)
            speed = 1.5f
            loop(true)
            setRenderMode(RenderMode.AUTOMATIC)
            playAnimation()
            setPaddingRelative(0, paddingInPx, 0, paddingInPx)
        }
        val dpAsPixels = (30 * scale + 0.5f).roundToInt()
        val textView = TextView(this).apply {
            text = message
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
            setTextColor(ContextCompat.getColor(this@CustomSplashService, R.color.white))
            setPadding(dpAsPixels,dpAsPixels,dpAsPixels,dpAsPixels)
        }

        layout = LinearLayout(this).apply {
            setBackgroundColor(R.color.blue_background)
            orientation = LinearLayout.VERTICAL
            addView(lottie, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0).apply {
                gravity = Gravity.CENTER
                weight = .7f
            })
            addView(textView, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0).apply {
                gravity = Gravity.CENTER
                weight = .3f
            })
        }

        wm.addView(layout, WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                when {
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                    else -> WindowManager.LayoutParams.TYPE_PHONE
                },
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.RGB_565))
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Handler().postDelayed({
            layout?.let {
                wm.removeView(layout)
                layout = null
            }
        }, 500)
    }
}