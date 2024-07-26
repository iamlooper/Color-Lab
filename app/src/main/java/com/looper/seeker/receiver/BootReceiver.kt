package com.looper.seeker.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.looper.seeker.service.BootService

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            // Start boot service.
            context?.startForegroundService(Intent(context, BootService::class.java))
        }
    }
}