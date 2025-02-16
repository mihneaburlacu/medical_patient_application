package com.example.new_medical_application.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.new_medical_application.common.AppConstants.ACTION_NEW_VALUE
import com.example.new_medical_application.common.AppConstants.ALERT_CHANNEL_ID
import com.example.new_medical_application.common.AppConstants.COLLECTOR_CHANNEL_ID
import com.example.new_medical_application.common.AppConstants.COLLECTOR_NOTIFICATION_ID
import com.example.new_medical_application.common.AppConstants.EXTRA_RANDOM_VALUE
import com.example.new_medical_application.common.AppConstants.HIGH_ALERT_NOTIFICATION_ID
import com.example.new_medical_application.common.AppConstants.LOW_ALERT_NOTIFICATION_ID

class ValueCollectorService : Service() {
    private val valueReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.getIntExtra(EXTRA_RANDOM_VALUE, -1)?.let { value ->
                Log.d("Mihnea123", "Received value: $value")
                if (value < 50) {
                    showNotification(
                        "⚠ Critical Low Value!",
                        "Value dropped to $value for HRV",
                        LOW_ALERT_NOTIFICATION_ID
                    )
                } else if (value > 120) {
                    showNotification(
                        "⚠ High Value Alert!",
                        "Value rose to $value for HRV",
                        HIGH_ALERT_NOTIFICATION_ID
                    )
                }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(COLLECTOR_NOTIFICATION_ID, buildNotification())

        val filter = IntentFilter(ACTION_NEW_VALUE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(valueReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
        } else {
            registerReceiver(valueReceiver, filter)
        }

    }

    override fun onDestroy() {
        unregisterReceiver(valueReceiver)
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = getSystemService(NotificationManager::class.java)

            val channel = NotificationChannel(
                COLLECTOR_CHANNEL_ID, "Collector Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val alertChannel = NotificationChannel(
                ALERT_CHANNEL_ID, "Alert Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            manager?.createNotificationChannel(channel)
            manager?.createNotificationChannel(alertChannel)
        }
    }

    private fun showNotification(title: String, message: String, notificationId: Int) {
        val notification = NotificationCompat.Builder(this, ALERT_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(android.R.drawable.stat_notify_error)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        val manager = getSystemService(NotificationManager::class.java)
        manager?.notify(notificationId, notification)
    }

    private fun buildNotification(): Notification {
        return NotificationCompat.Builder(this, COLLECTOR_CHANNEL_ID)
            .setContentTitle("Value Collector Running")
            .setContentText("Checking generated values...")
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .build()
    }
}