package com.example.servicestestapp

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.JobIntentService
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*

class MyJobIntentService: JobIntentService() {

    // создание сервиса
    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onHandleWork(intent: Intent) {
        log("onHandleIntent")
        val page = intent.getIntExtra(PAGE, 0) ?: 0
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
        Log.d("ServiceTag","MyJobIntentService() $msg")
    }


    companion object {
        private const val PAGE = "page"
        private const val JOB_ID = 111

        fun enqueue(context: Context, page: Int){
            enqueueWork(
                context,
                MyJobIntentService::class.java,
                JOB_ID,
                newIntent(context, page)
            )
        }

       private fun newIntent(context: Context, page:Int):Intent {
            return Intent(context,MyJobIntentService::class.java).apply {
                putExtra(PAGE,page)
            }
        }
    }

}