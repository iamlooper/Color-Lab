package com.looper.seeker.activity

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.navigation.NavController
import androidx.preference.PreferenceManager
import com.looper.android.support.activity.NoNavigationActivity
import com.looper.android.support.util.PermissionUtils
import com.looper.seeker.MyApp
import com.looper.seeker.R
import com.looper.seeker.provider.RootConnectionProvider
import com.looper.seeker.worker.TippingWorker

class MainActivity : NoNavigationActivity() {

    private val destinationChangeListener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.fragment_main) {
                supportActionBar?.title = getString(R.string.app_name)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup navigation.
        setupNavigation(R.navigation.mobile_navigation, R.id.fragment_main)

        // Request for notification permission on A13+.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            PermissionUtils.request(this, this, Manifest.permission.POST_NOTIFICATIONS)
        }

        // Initialize root connection provider.
        MyApp.rootConnectionProvider = RootConnectionProvider(this)

        // Start tipping worker.
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        TippingWorker.scheduleTipping(
            this,
            sharedPreferences.getBoolean("pref_disable_tipping", false),
            sharedPreferences.getString("pref_tipping_interval", "1").toInt()
        )
    }

    override fun onStart() {
        super.onStart()
        navController.addOnDestinationChangedListener(destinationChangeListener)
    }

    override fun onStop() {
        super.onStop()
        navController.removeOnDestinationChangedListener(destinationChangeListener)
    }
}