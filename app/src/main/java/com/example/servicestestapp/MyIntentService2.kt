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

class MyIntentService2: IntentService(NAME) {

    // создание сервиса
    override fun onCreate() {
        super.onCreate()
        setIntentRedelivery(true)
        log("onCreate")
    }

    override fun onHandleIntent(intent: Intent?) {
        log("onHandleIntent")
        val page = intent?.getIntExtra(PAGE, 0) ?: 0
        for (i in 0 until 5) {
            Thread.sleep(1000)
            log("Timer ${i+1} ${page+1}")
        }
    }

    // сервис уничтож.
    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    private fun log(msg:String){
        Log.d("ServiceTag","MyIntentService2() $msg")
    }


    companion object {
        private const val NAME = "MyIntentService2"
        private const val PAGE = "page"

        fun newIntent(context: Context, page:Int):Intent {
            return Intent(context,MyIntentService2::class.java).apply {
                putExtra(PAGE,page)
            }
        }
    }

}