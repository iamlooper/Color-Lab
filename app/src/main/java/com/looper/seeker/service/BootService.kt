package com.looper.seeker.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.ServiceCompat
import com.looper.android.support.util.NotificationUtils
import com.looper.android.support.util.SharedPreferencesUtils
import com.looper.seeker.R
import com.looper.seeker.provider.RootConnectionProvider
import com.looper.seeker.util.FabricatedOverlayUtils

class BootService : Service() {

    private lateinit var sharedPreferencesUtils: SharedPreferencesUtils
    private lateinit var fabricatedOverlayUtils: FabricatedOverlayUtils

    @SuppressLint("InlinedApi")
    override fun onCreate() {
        super.onCreate()

        // Build foreground notification.
        val foregroundNotification = NotificationUtils.createNotificationBuilder(
            this,
            "theming_notification",
            R.drawable.ic_brush
        )
            .setContentTitle(getString(R.string.theme_on_boot))
            .build()

        // Start the foreground service.
        ServiceCompat.startForeground(
            this,
            1,
            foregroundNotification,
            ServiceInfo.FOREGROUND_SERVICE_TYPE_SHORT_SERVICE
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Initialize variables.
        sharedPreferencesUtils = SharedPreferencesUtils(this)
        fabricatedOverlayUtils = FabricatedOverlayUtils()

        // If apply theme on boot is enabled, apply the theme else disable it.
        val disableTheming = sharedPreferencesUtils.get("pref_disable_theming", emptySet<String>())
        if (sharedPreferencesUtils.get(
                "pref_apply_theme_on_boot",
                true
            ) && disableTheming.size != 5
        ) {
            RootConnectionProvider(
                this,
                onSuccess = { ipc ->
                    ipc?.setEnabled("android", "com.looper.seeker.overlay", true)

                    // Build the notification.
                    val builder = NotificationUtils.createNotificationBuilder(
                        this,
                        "theming_notification",
                        R.drawable.ic_brush
                    )
                        .setContentTitle(getString(R.string.theme_on_boot))
                        .setContentText(getString(R.string.applied_theme_on_boot))

                    // Show the notification.
                    NotificationUtils.displayNotification(this, 2, builder)
                },
                onFailure = {
                    Toast.makeText(
                        this,
                        getString(R.string.root_not_found), Toast.LENGTH_LONG
                    ).show()
                    ServiceCompat.stopForeground(this, ServiceCompat.STOP_FOREGROUND_REMOVE)
                    stopSelf()
                }
            )
        } else {
            RootConnectionProvider(
                this,
                onSuccess = { ipc ->
                    ipc?.setEnabled("android", "com.looper.seeker.overlay", false)
                }
            )
        }

        // Stop the service.
        ServiceCompat.stopForeground(this, ServiceCompat.STOP_FOREGROUND_REMOVE)
        stopSelf()

        // Not restart the service if it gets terminated.
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        // Don't need to provide binding for this service, so return null.
        return null
    }
}
