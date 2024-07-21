package com.looper.seeker.activity

import android.os.Bundle

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.looper.android.support.activity.BaseActivity
import com.looper.seeker.R

open class NoNavigationActivity : BaseActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    protected lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set content view.
        setContentView(getContentView())

        // Initialize and set up the toolbar as the action bar.
        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Find the navigation controller.
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    protected fun setupNavigation(navGraphId: Int, fragmentId: Int) {
        // Inflate the navigation graph.
        val navInflater = navController.navInflater
        val navGraph = navInflater.inflate(navGraphId)

        // Set the inflated navigation graph as the graph for the navigation controller.
        navController.graph = navGraph

        // Setup app bar with navigation controller.
        appBarConfiguration = AppBarConfiguration(navGraph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Navigate to given fragment.
        navController.navigate(fragmentId)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    protected open fun getContentView(): Int {
        return -1
    }
}