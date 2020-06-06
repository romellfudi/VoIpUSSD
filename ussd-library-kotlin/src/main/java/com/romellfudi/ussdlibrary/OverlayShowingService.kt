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
import android.widget.Button

/**
 * OverlayShowingService for Android progressing dialog
 *
 * @author Romell Dominguez
 * @version 1.1.i 2019/04/18
 * @since 1.1.i
 */
class OverlayShowingService : Service() {

    private var overlayedButton: Button? = null

    private var wm: WindowManager? = null

    private var tittle: String? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (intent.hasExtra(EXTRA))
            tittle = intent.getStringExtra(EXTRA)
        wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val size = Point()
        wm!!.defaultDisplay.getSize(size)
        val flagLayout: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }
        overlayedButton = Button(this)
        overlayedButton!!.text = tittle
        overlayedButton!!.alpha = 0.7f
        overlayedButton!!.setBackgroundColor(-0x1)
        overlayedButton!!.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26f)
        var operation = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        val params = WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, size.y - 200,
                flagLayout, operation, PixelFormat.TRANSLUCENT)
        params.gravity = Gravity.CENTER or Gravity.CENTER
        wm!!.addView(overlayedButton, params)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Handler().postDelayed({
            overlayedButton?.let {
                wm!!.removeView(overlayedButton)
                overlayedButton = null
            }
        }, 500)
    }

    companion object {
        val EXTRA = "TITTLE"
    }

}