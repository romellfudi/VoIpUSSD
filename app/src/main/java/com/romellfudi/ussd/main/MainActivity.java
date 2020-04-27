/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.main;

import android.content.IntentSender;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.rbddevs.splashy.Splashy;
import com.romellfudi.ussd.R;
import com.romellfudi.ussd.di.component.ActivityComponent;
import com.romellfudi.ussd.di.component.DaggerActivityComponent;
import com.romellfudi.ussd.di.module.ActivityModule;
import com.romellfudi.ussdlibrary.BuildConfig;

import javax.inject.Inject;

/**
 * Main Activity
 *
 * @author Romell Domínguez
 * @version 1.0.b 23/02/2017
 * @since 1.0
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        InstallStateUpdatedListener {

    @Inject
    AppUpdateManager appUpdateManager;

    private ActivityComponent activityComponent;

    private Task<AppUpdateInfo> appUpdateInfoTask;
    private static final int REQUEST_CODE_FLEXIBLE_UPDATE = 1234;

    public ActivityComponent getActivityComponent() {
        if (activityComponent==null){
            activityComponent = DaggerActivityComponent.builder()
//                    .appComponent(((App)getApplicationContext()).getAppComponent())
                    .activityModule(new ActivityModule(this))
                    .build();
        }
        return activityComponent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        ((App)getApplicationContext()).getAppComponent().inject(this);
        getActivityComponent().inject(this);
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null)
            splashy();
        setContentView(R.layout.activity_main_menu);

        appUpdateManager.registerListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.title_activity_cp1));
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction fragment = getSupportFragmentManager().beginTransaction();
        fragment.replace(R.id.fragment_layout, new MainFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();

    }

    private void checkUpdate() {
        appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                showMessage("updateAvailability: " + appUpdateInfo.updateAvailability() +
                        " isUpdateTypeAllowed: " + appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE));
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    requestUpdate(appUpdateInfo);
                }
            }
        });
    }

    private void requestUpdate(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.IMMEDIATE,
                    MainActivity.this,
                    REQUEST_CODE_FLEXIBLE_UPDATE);
        } catch (IntentSender.SendIntentException e) {
            showMessage("Request update error");
        }
    }

    private void splashy() {
        new Splashy(MainActivity.this)
                .setLogo(R.drawable.combine)
                .setLogoScaleType(ImageView.ScaleType.FIT_CENTER)
                .setAnimation(Splashy.Animation.GROW_LOGO_FROM_CENTER, 500)
                .setTitle(R.string.app_name)
                .setTitleColor(R.color.black)
                .setSubTitle("Version  " + BuildConfig.VERSION_NAME)
                .setProgressColor(R.color.black)
                .setBackgroundResource(R.color.splash)
                .setFullScreen(true)
                .setTime(2000)
                .show();
        Splashy.Companion.onComplete(new Splashy.OnComplete() {
            @Override
            public void onComplete() {
                checkUpdate();
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment newFragment = null;
        String tittle = null;
        if (id == R.id.op1) {
            newFragment = new MainFragment();
            tittle = getResources().getString(R.string.title_activity_cp1);
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        getSupportActionBar().setTitle(tittle);
        ft.replace(R.id.fragment_layout, newFragment); // f1_container is your FrameLayout container
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStateUpdate(InstallState installState) {
        if (installState.installStatus() == InstallStatus.DOWNLOADED) {
            showMessage("Has been Downloaded!!!");
            notifyUser();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        appUpdateManager.unregisterListener(MainActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {
                if (result.installStatus() == InstallStatus.DOWNLOADED) {
                    notifyUser();
                }
            }
        });
    }

    private void showMessage(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }

    private void notifyUser() {
        Snackbar.make(findViewById(android.R.id.content), "Restart to update", Snackbar.LENGTH_INDEFINITE)
                .setAction("Restart to update", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        appUpdateManager.completeUpdate();
                        appUpdateManager.unregisterListener(MainActivity.this);
                    }
                }).show();
    }
}
