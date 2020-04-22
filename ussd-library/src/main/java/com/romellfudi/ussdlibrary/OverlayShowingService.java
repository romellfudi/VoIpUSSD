/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussdlibrary;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;

/**
 * OverlayShowingService for Android progressing dialog
 *
 * @author Romell Dominguez
 * @version 1.0.a 23/02/2017
 * @since 1.0
 */
public class OverlayShowingService extends Service {

    private Button overlayedButton;

    private WindowManager wm;

    public final static String EXTRA = "TITTLE";

    private String tittle = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.hasExtra(EXTRA))
            tittle = intent.getStringExtra(EXTRA);
        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }
        overlayedButton = new Button(this);
        overlayedButton.setText(tittle);
        overlayedButton.setAlpha(0.7f);
        overlayedButton.setBackgroundColor(0xFFFFFFFF);
        overlayedButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);

        WindowManager.LayoutParams params =
                new WindowManager.LayoutParams
                        (WindowManager.LayoutParams.MATCH_PARENT,
                                size.y - 200,
                                LAYOUT_FLAG
                                , WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.CENTER | Gravity.CENTER;
        wm.addView(overlayedButton, params);
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
                if (overlayedButton != null) {
                    wm.removeView(overlayedButton);
                    overlayedButton = null;
                }
            }
        }, 500);
    }

}