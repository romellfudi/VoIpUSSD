package com.romellfudi.ussd.sample;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.romellfudi.ussd.R;

/**
 * Main Activity
 *
 * @author Romell Domínguez
 * @version 1.0.b 23/02/2017
 * @since 1.0
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction fragment = getSupportFragmentManager().beginTransaction();
        fragment.replace(R.id.fragment_layout, new MainFragment()); // f1_container is your FrameLayout container
        fragment.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragment.addToBackStack(null);
        fragment.commit();
        getSupportActionBar().setTitle(getString(R.string.title_activity_cp1));
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
        // Handle navigation view item clicks here.
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

}
