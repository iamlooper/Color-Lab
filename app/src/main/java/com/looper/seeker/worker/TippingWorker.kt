package com.looper.seeker.worker

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.looper.android.support.util.NotificationUtils
import com.looper.seeker.R
import java.util.concurrent.TimeUnit

class TippingWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        // Create an Intent to open a URL.
        val url = "https://www.pling.com/p/2163392/startdownload?file_id=1717964287"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Build the notification.
        val builder = NotificationUtils.createNotificationBuilder(
            applicationContext,
            "tipping_notification",
            R.drawable.ic_volunteer_activism
        )
            .setContentTitle(applicationContext.getString(R.string.tipping))
            .setContentText(applicationContext.getString(R.string.tipping_notification_content))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        // Show the notification.
        NotificationUtils.displayNotification(applicationContext, 3, builder)

        return Result.success()
    }

    companion object {
        fun scheduleTipping(context: Context, disableTipping: Boolean, tippingInterval: Int) {
            val workManager = WorkManager.getInstance(context)

            if (!disableTipping) {
                val repeatInterval = when (tippingInterval) {
                    0 -> 1L // Daily
                    1 -> 7L // Weekly
                    2 -> 14L // Two-weekly
                    else -> 7L // Weekly
                }

                val timerWorkRequest =
                    PeriodicWorkRequestBuilder<TippingWorker>(repeatInterval, TimeUnit.DAYS)
                        .addTag("tipping_worker")
                        .setInitialDelay(repeatInterval, TimeUnit.DAYS)
                        .build()

                workManager.enqueueUniquePeriodicWork(
                    "TippingWork",
                    ExistingPeriodicWorkPolicy.UPDATE,
                    timerWorkRequest
                )
            } else {
                workManager.cancelAllWorkByTag("tipping_worker")
            }
        }
    }
}