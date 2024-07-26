package com.looper.seeker.activity

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.navigation.NavController
import com.looper.android.support.util.PermissionUtils
import com.looper.seeker.MyApp
import com.looper.seeker.R
import com.looper.seeker.provider.RootConnectionProvider

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
            PermissionUtils.requestPermission(
                activity = this,
                context = this,
                permission = Manifest.permission.POST_NOTIFICATIONS
            ).invoke()
        }

        // Initialize root connection provider.
        MyApp.rootConnectionProvider = RootConnectionProvider(this)
    }

    override fun onStart() {
        super.onStart()
        navController.addOnDestinationChangedListener(destinationChangeListener)
    }

    override fun onStop() {
        super.onStop()
        navController.removeOnDestinationChangedListener(destinationChangeListener)
    }

    override fun getContentView(): Int {
        return R.layout.activity_main
    }
}