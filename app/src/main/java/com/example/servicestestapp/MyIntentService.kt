package com.example.servicestestapp

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*

class MyIntentService: IntentService(NAME) {

    // создание сервиса
    override fun onCreate() {
        super.onCreate()
        //setIntentRedelivery(true)
        log("onCreate")
        createNotificationChannel()
        startForeground(NOTIFICATION_ID,createNotification())
    }

    override fun onHandleIntent(p0: Intent?) {
        log("onHandleIntent")
        for (i in 0 until 5) {
            Thread.sleep(1000)
            log("Timer ${i+1}")
        }
    }

    // сервис уничтож.
    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    private fun log(msg:String){
        Log.d("ServiceTag","MyIntentService() $msg")
    }

    private fun createNotificationChannel(){
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun createNotification():Notification =
            NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Title")
                .setContentText("Text")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()


    companion object {
        private const val CHANNEL_ID = "channel_id"
        private const val CHANNEL_NAME = "channel_name"
        private const val NOTIFICATION_ID = 1
        private const val NAME = "MyIntentService"

        fun newIntent(context: Context):Intent {
            return Intent(context,MyIntentService::class.java)
        }
    }

}