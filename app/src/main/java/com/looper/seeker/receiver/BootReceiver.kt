package com.looper.seeker.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.preference.PreferenceManager
import com.looper.seeker.service.BootService
import com.looper.seeker.worker.TippingWorker

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            // Start boot service.
            context?.startForegroundService(Intent(context, BootService::class.java))

            // Start tipping worker.
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context!!)
            TippingWorker.scheduleTipping(
                context,
                sharedPreferences.getBoolean("pref_disable_tipping", false),
                sharedPreferences.getString("pref_tipping_interval", "1").toInt()
            )
        }
    }
}