package com.example.servicestestapp

import android.content.Context
import android.util.Log
import androidx.work.*

class MyWorker(
    context: Context,
    private val workerParameters: WorkerParameters
): Worker(context,workerParameters) {

    override fun doWork(): Result {
        log("doWork")
        val page = workerParameters.inputData.getInt(PAGE,0)
        for (i in 0 until 5) {
            Thread.sleep(1000)
            log("Timer ${i+1} ${page+1}")
        }
        return Result.success()
    }

    private fun log(msg:String){
        Log.d("ServiceTag","MyWorker() $msg")
    }

    companion object{
        private const val PAGE = "page"
        const val WORK_NAME = "work name"

        fun makeRequest(page:Int):OneTimeWorkRequest{
            return OneTimeWorkRequestBuilder<MyWorker>()
                .setInputData(workDataOf(PAGE to page))
                .setConstraints(makeConstraints())
                .build()
        }

        private fun makeConstraints():Constraints{
            return Constraints.Builder()
                .setRequiresCharging(true)
                .build()
        }

    }

}