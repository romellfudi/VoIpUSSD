package fudi.freddy.biox_ussd.act;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import fudi.freddy.biox_ussd.R;
import fudi.freddy.biox_ussd.use_case.CP1;
import fudi.freddy.ussdlibrary.USSDService;

public class MainMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_layout, new CP1()); // f1_container is your FrameLayout container
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
        getSupportActionBar().setTitle(getString(R.string.title_activity_cp13));

//        Settings.Secure.putString(getContentResolver(),
//                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES, "fudi.freddy.biox_ussd/"+USSDService.class.getSimpleName());
//        Settings.Secure.putString(getContentResolver(),
//                Settings.Secure.ACCESSIBILITY_ENABLED,"1");
//        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
//        startActivityForResult(intent,0);
        if (!isAccessibilitySettingsOn(getApplicationContext())) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    MainMenuActivity.this);
            alertDialogBuilder.setTitle("Permisos de accesibilidad");
            alertDialogBuilder
                    .setMessage("Debe habilitar los permisos de accesibilidad para la app '"+getString(R.string.app_name)+"'")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    startActivityForResult(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), 1);
                                }
                            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==1){
            if (!isAccessibilitySettingsOn(getApplicationContext())) {
                Toast.makeText(MainMenuActivity.this,"Debe habilitar los permisos de accesibilidad para la app "+getString(R.string.app_name),Toast.LENGTH_SHORT).show();

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        MainMenuActivity.this);
                alertDialogBuilder.setTitle("Permisos de accesibilidad");
                alertDialogBuilder
                        .setMessage("Debe habilitar los permisos de accesibilidad para la app '"+getString(R.string.app_name)+"'")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivityForResult(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), 1);
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            } else
                Toast.makeText(MainMenuActivity.this,"Conforme",Toast.LENGTH_SHORT).show();
        }
    }

    private String TAG = MainMenuActivity.class.getSimpleName();
    private boolean isAccessibilitySettingsOn(Context mContext) {
        int accessibilityEnabled = 0;
        final String service = getPackageName() + "/" + USSDService.class.getCanonicalName();
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED);
            Log.v(TAG, "accessibilityEnabled = " + accessibilityEnabled);
        } catch (Settings.SettingNotFoundException e) {
            Log.e(TAG, "Error finding setting, default accessibility to not found: "
                    + e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            Log.v(TAG, "***ACCESSIBILITY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();

                    Log.v(TAG, "-------------- > accessibilityService :: " + accessibilityService + " " + service);
                    if (accessibilityService.equalsIgnoreCase(service)) {
                        Log.v(TAG, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        } else {
            Log.v(TAG, "***ACCESSIBILITY IS DISABLED***");
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
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
            newFragment = new CP1();
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
