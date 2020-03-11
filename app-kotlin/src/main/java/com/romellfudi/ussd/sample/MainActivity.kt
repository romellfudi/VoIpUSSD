package com.romellfudi.ussd.sample

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.romellfudi.ussd.R
import kotlinx.android.synthetic.main.activity_main_menu.*
import kotlinx.android.synthetic.main.app_bar_main_menu.*

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
        setSupportActionBar(toolbar)
        nav_view.setNavigationItemSelectedListener(this)

        with(supportFragmentManager.beginTransaction()) {
            replace(R.id.fragment_layout, MainFragment()) // f1_container is your FrameLayout container
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            addToBackStack(null)
            commit()
        }
        supportActionBar?.title = getString(R.string.title_activity_cp1)

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            finish()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        var newFragment: Fragment? = null
        var tittle: String? = null
        if (id == R.id.op1) {
            newFragment = MainFragment()
            tittle = resources.getString(R.string.title_activity_cp1)
        }
        supportActionBar?.title = tittle
        with(supportFragmentManager.beginTransaction()) {
            replace(R.id.fragment_layout, newFragment!!)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            addToBackStack(null)
            commit()
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}
