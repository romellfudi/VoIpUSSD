package com.romellfudi.ussd.sample

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.romellfudi.ussd.R

/**
 * Main Activity
 *
 * @author Romell Domínguez
 * @version 1.0.b 23/02/2017
 * @since 1.0
 */
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        setSupportActionBar(findViewById<View>(R.id.toolbar) as Toolbar)
        (findViewById<View>(R.id.nav_view) as NavigationView).setNavigationItemSelectedListener(this)

        with(supportFragmentManager.beginTransaction()) {
            replace(R.id.fragment_layout, MainFragment()) // f1_container is your FrameLayout container
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            addToBackStack(null)
            commit()
        }
        supportActionBar?.title = getString(R.string.title_activity_cp1)

    }

    override fun onBackPressed() {
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            finish()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId
        var newFragment: Fragment? = null
        var tittle: String? = null
        if (id == R.id.op1) {
            newFragment = MainFragment()
            tittle = resources.getString(R.string.title_activity_cp1)
        }
        supportActionBar?.title = tittle
        with(supportFragmentManager.beginTransaction()) {
            replace(R.id.fragment_layout, newFragment!!) // f1_container is your FrameLayout container
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            addToBackStack(null)
            commit()
        }
        (findViewById<View>(R.id.drawer_layout) as DrawerLayout).closeDrawer(GravityCompat.START)
        return true
    }

}
