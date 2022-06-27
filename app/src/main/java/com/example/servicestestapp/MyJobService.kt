package com.example.servicestestapp

import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class MyJobService: JobService() {

    private val scope = CoroutineScope(Dispatchers.Main)

    // создание сервиса
    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onStartJob(p0: JobParameters?): Boolean {
        log("onStartJob")
        scope.launch {
            for (i in 0 until 100) {
                delay(1000)
                log("Timer ${i+1}")
            }
            jobFinished(p0,true)
        }
        return true
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        log("onStopJob")
        return true
    }

    // сервис уничтож.
    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
        log("onDestroy")
    }

    private fun log(msg:String){
        Log.d("ServiceTag","MyJobService() $msg")
    }

    companion object{
        const val JOB_ID = 100
    }

}