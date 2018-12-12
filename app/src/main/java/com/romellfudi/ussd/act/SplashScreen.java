package com.romellfudi.ussd.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.romellfudi.ussd.R;

/**
 *
 * @author Romell Domínguez
 * @version 1.0.0 12/12/2018
 * @since 1.1
 */
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        final Context me = this;
        Intent mIntent = getIntent();
        final int time = mIntent.getIntExtra("time", 3000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(me, MainMenuActivity.class);
                intent.putExtra("stop", true);
                startActivity(intent);
                finish();
            }
        }, time);
    }
}
