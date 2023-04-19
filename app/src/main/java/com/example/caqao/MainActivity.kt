package com.example.caqao

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.caqao.databinding.ActivityMainBinding
import com.example.caqao.fragments.*
import com.google.android.material.navigation.NavigationView
import androidx.navigation.ui.setupWithNavController



class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var navController: NavController
    private lateinit var bottomNavigation: MeowBottomNavigation
    private lateinit var drawerLayout: DrawerLayout
    private var isSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        //drawer
        drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val navView = findViewById<NavigationView>(R.id.navView)
        navView.setupWithNavController(navController)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_nav,
            R.string.close_nav,
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // bottom nav
        bottomNavigation = findViewById(R.id.bottomNavigation)
        bottomNavigation.show(R.id.homeFragment)
        bottomNavigation.add(MeowBottomNavigation.Model(R.id.galleryFragment , R.drawable.ic_photo))
        bottomNavigation.add(MeowBottomNavigation.Model(R.id.homeFragment, R.drawable.ic_focus))
//        bottomNavigation.add(MeowBottomNavigation.Model(R.id.analyticsFragment, R.drawable.ic_analytics))

        bottomNavigation.setOnClickMenuListener {
            when (it.id) {
                R.id.homeFragment -> {
                    replaceFragment(HomeFragment.newInstance())
                    supportActionBar!!.title = "Home"
                }
                R.id.galleryFragment -> {
                    replaceFragment(GalleryFragment.newInstance())
                    supportActionBar!!.title = "Gallery"
                }
                R.id.analyticsFragment -> {
                    replaceFragment(AnalyticsFragment.newInstance())
                    supportActionBar!!.title = "Analytics"
                }

                else -> {
                    replaceFragment(HomeFragment.newInstance())
                }
            }
        }

        //drawer layout
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, HomeFragment()).commit()
                    supportActionBar!!.title = "Home"
                }
                R.id.AboutUsFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, AboutUsFragment()).commit()
                }
                R.id.FAQFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, FAQFragment()).commit()
                    supportActionBar!!.title = "FAQ"
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

    }


    private fun replaceFragment(fragment: Fragment){
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.nav_host_fragment,fragment).commit()
    }


    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            val fragmentTransition = supportFragmentManager.beginTransaction()
            fragmentTransition.replace(R.id.nav_host_fragment,HomeFragment()).commit()
            bottomNavigation.show(R.id.homeFragment, true)
            supportActionBar?.show()
            supportActionBar!!.title = "Home"
            if (isSelected) {
                isSelected = false
            } else {
//                bottomNavigation.show(2, true)
//                isSelected = true
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    fun logout (menuItem: MenuItem) {
        startActivity(Intent(applicationContext, MainActivity2::class.java))
        finish()
    }
}