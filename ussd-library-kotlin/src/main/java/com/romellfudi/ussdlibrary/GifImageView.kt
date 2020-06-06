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

import android.content.Context
import android.graphics.Canvas
import android.graphics.Movie
import android.net.Uri
import android.os.SystemClock
import android.util.AttributeSet
import android.util.Log
import android.view.View

import java.io.FileNotFoundException
import java.io.InputStream

/**
 * Designed view Android splashing dialog
 *
 * @author Romell Dominguez
 * @version 1.1.i 2019/04/18
 * @since 1.1.i
 */
class GifImageView : View {

    private var mInputStream: InputStream? = null
    private var mMovie: Movie? = null
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var mStart: Long = 0
    private var mContext: Context? = null

    constructor(context: Context) : super(context) {
        this.mContext = context
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr) {
        this.mContext = context
        if (attrs.getAttributeName(1) == "background") {
            val id = Integer.parseInt(attrs.getAttributeValue(1).substring(1))
            setGifImageResource(id)
        }
    }

    private fun init() {
        isFocusable = true
        mMovie = Movie.decodeStream(mInputStream)
        mWidth = mMovie!!.width()
        mHeight = mMovie!!.height()
        requestLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onDraw(canvas: Canvas) {
        val now = SystemClock.uptimeMillis()
        if (mStart == 0L) {
            mStart = now
        }
        mMovie?.let {
            var duration = mMovie!!.duration()
            if (duration == 0) {
                duration = 1000
            }
            val relTime = ((now - mStart) % duration).toInt()
            mMovie!!.setTime(relTime)
            mMovie!!.draw(canvas, 0f, 0f)
            invalidate()
        }
    }

    fun setGifImageResource(id: Int) {
        mInputStream = mContext!!.resources.openRawResource(id)
        init()
    }

    fun setGifImageUri(uri: Uri) {
        try {
            mInputStream = mContext!!.contentResolver.openInputStream(uri)
            init()
        } catch (e: FileNotFoundException) {
            Log.e("GIfImageView", "File not found")
        }

    }
}