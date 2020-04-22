/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussdlibrary;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * SplashLoadingService for Android splashing dialog
 *
 * @author Romell Dominguez
 * @version 1.1.d 23/02/2017
 * @since 1.1.d
 */
public class SplashLoadingService extends Service {

    private LinearLayout layout;
    private WindowManager wm;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("ResourceAsColor")
    public int onStartCommand(Intent intent, int flags, int startId) {
        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }

        int padding_in_dp = 100;
        final float scale = getResources().getDisplayMetrics().density;
        int padding_in_px = (int) (padding_in_dp * scale + 0.5f);

        layout = new LinearLayout(this);
        layout.setBackgroundColor(R.color.blue_background);
        layout.setOrientation(LinearLayout.VERTICAL);

        WindowManager.LayoutParams params =
                new WindowManager.LayoutParams
                        (WindowManager.LayoutParams.MATCH_PARENT,
                                WindowManager.LayoutParams.MATCH_PARENT,
                                LAYOUT_FLAG
                                , WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                                PixelFormat.RGB_565);

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.favico);
        imageView.setPaddingRelative(0,padding_in_px,0,padding_in_px);
        LinearLayout.LayoutParams params_ll = new LinearLayout
                .LayoutParams(LinearLayout.MarginLayoutParams.MATCH_PARENT, 0);
        params_ll.gravity = Gravity.CENTER;
        params_ll.weight = 1;

        RelativeLayout relativeLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        rp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        relativeLayout.addView(imageView,rp);
        layout.addView(relativeLayout, params_ll);

        GifImageView gifImageView = new GifImageView(this);
        gifImageView.setGifImageResource(R.drawable.loading);
        gifImageView.setPaddingRelative(0,padding_in_px,0,padding_in_px);

        relativeLayout = new RelativeLayout(this);
        rp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        rp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        relativeLayout.addView(gifImageView,rp);

        layout.addView(relativeLayout,params_ll);

        wm.addView(layout,params);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (layout != null) {
                    wm.removeView(layout);
                    layout = null;
                }
            }
        },500);
    }

}