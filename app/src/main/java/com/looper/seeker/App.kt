package com.looper.seeker

import android.annotation.SuppressLint
import com.google.android.material.color.DynamicColors
import com.looper.android.support.App
import com.looper.android.support.util.NotificationUtils
import com.looper.seeker.provider.RootConnectionProvider
import org.lsposed.hiddenapibypass.HiddenApiBypass

class MyApp : App() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var rootConnectionProvider: RootConnectionProvider? = null
    }

    override fun onCreate() {
        super.onCreate()

        // Apply dynamic colors.
        DynamicColors.applyToActivitiesIfAvailable(this)

        // Get access to hidden APIs.
        HiddenApiBypass.setHiddenApiExemptions("L")

        // Build notification channels.
        NotificationUtils.buildChannel(
            this,
            "theming_notification",
            getString(R.string.theming)
        )
        NotificationUtils.buildChannel(
            this,
            "tipping_notification",
            applicationContext.getString(R.string.tipping)
        )
    }
}