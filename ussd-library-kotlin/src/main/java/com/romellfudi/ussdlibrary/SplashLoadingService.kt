/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

/**
 * BoostTag E.I.R.L. All Copyright Reserved
 * www.boosttag.com
 */
package com.romellfudi.ussdlibrary

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.graphics.Point
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout

/**
 * SplashLoadingService for Android splashing dialog
 *
 * @author Romell Dominguez
 * @version 1.1.i 2019/04/18
 * @since 1.1.i
 */
class SplashLoadingService : Service() {

    private var layout: LinearLayout? = null
    private lateinit var wm: WindowManager

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    @SuppressLint("ResourceAsColor")
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val size = Point()
        wm.defaultDisplay.getSize(size)
        val LAYOUT_FLAG: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        else WindowManager.LayoutParams.TYPE_PHONE

        val paddingInDp = 100
        val scale = resources.displayMetrics.density
        val paddingInPx = (paddingInDp * scale + 0.5f).toInt()

        layout = LinearLayout(this)
        layout!!.setBackgroundColor(R.color.blue_background)
        layout!!.orientation = LinearLayout.VERTICAL

        val params = WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                LAYOUT_FLAG, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.RGB_565)

        val imageView = ImageView(this)
        imageView.setImageResource(R.drawable.favico)
        imageView.setPaddingRelative(0, paddingInPx, 0, paddingInPx)
        val params_ll = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0)
        params_ll.gravity = Gravity.CENTER
        params_ll.weight = 1f

        var relativeLayout = RelativeLayout(this)
        var rp = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        rp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
        relativeLayout.addView(imageView, rp)
        layout!!.addView(relativeLayout, params_ll)

        val gifImageView = GifImageView(this)
        gifImageView.setGifImageResource(R.drawable.loading)
        gifImageView.setPaddingRelative(0, paddingInPx, 0, paddingInPx)

        relativeLayout = RelativeLayout(this)
        rp = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        rp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
        relativeLayout.addView(gifImageView, rp)

        layout!!.addView(relativeLayout, params_ll)

        wm!!.addView(layout, params)
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