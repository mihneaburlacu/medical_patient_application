package com.example.new_medical_application.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.new_medical_application.common.AppConstants.ACTION_NEW_VALUE
import com.example.new_medical_application.common.AppConstants.EXTRA_RANDOM_VALUE
import com.example.new_medical_application.common.AppConstants.GENERATOR_CHANNEL_ID
import com.example.new_medical_application.common.AppConstants.GENERATOR_NOTIFICATION_ID
import kotlin.random.Random

class ValueGeneratorService : Service() {
    private val handler = Handler(Looper.getMainLooper())
    private val interval = 30000L

    private val runnable = object : Runnable {
        override fun run() {
            val randomValue = Random.nextInt(20, 301)
            Log.d("Mihnea123", "sending $randomValue")
            sendBroadcast(Intent(ACTION_NEW_VALUE).apply {
                putExtra(EXTRA_RANDOM_VALUE, randomValue)
            })
            handler.postDelayed(this, interval)
        }
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(GENERATOR_NOTIFICATION_ID, buildNotification())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        handler.post(runnable)
        return START_STICKY
    }

    override fun onDestroy() {
        handler.removeCallbacks(runnable)
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                GENERATOR_CHANNEL_ID, "Generator Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }

    private fun buildNotification(): Notification {
        return NotificationCompat.Builder(this, GENERATOR_CHANNEL_ID)
            .setContentTitle("Value Generator Running")
            .setContentText("Generating random values...")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()
    }
}