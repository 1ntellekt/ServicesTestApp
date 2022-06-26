package com.example.servicestestapp

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class MyService: Service() {

    private val scope = CoroutineScope(Dispatchers.Main)

    // создание сервиса
    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    //работа сервиса
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        log("onStartCommand")
        val start = intent?.getIntExtra(EXTRA_START,0) ?: 0
        scope.launch {
            for (i in start until start+100) {
                delay(1000)
                log("Timer ${i+1}")
            }
        }
        return START_REDELIVER_INTENT
    }

    // сервис уничтож.
    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
        log("onDestroy")
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private fun log(msg:String){
        Log.d("ServiceTag","MyService() $msg")
    }

    companion object{

        private const val EXTRA_START = "extra_start"

        fun newIntent(context: Context, start: Int):Intent {
            return Intent(context,MyService::class.java).apply {
                putExtra(EXTRA_START,start)
            }
        }
    }

}