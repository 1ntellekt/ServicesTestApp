package com.example.servicestestapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.app.job.JobWorkItem
import android.content.ComponentName
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.servicestestapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding:ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.apply {
            btnService.setOnClickListener {
                stopService(MyForegroundService.newIntent(this@MainActivity))
                startService(MyService.newIntent(this@MainActivity,25))
            }
            btnForegroundService.setOnClickListener {
                ContextCompat.startForegroundService(
                    this@MainActivity,
                    MyForegroundService.newIntent(this@MainActivity)
                )
            }
            btnIntentService.setOnClickListener {
                ContextCompat.startForegroundService(
                    this@MainActivity,
                    MyIntentService.newIntent(this@MainActivity)
                )
            }
            btnJobScheduler.setOnClickListener {
                val componentName = ComponentName(this@MainActivity,MyJobService::class.java)
                val jobInfo = JobInfo.Builder(MyJobService.JOB_ID,componentName)
                    .setRequiresCharging(true) // работать будет при разярдке устройства
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED) // если на устройстве вкл Wifi
                    //.setPersisted(true)//если устройство выключ и включ
                    //.setPeriodic()
                    .build()
                val jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val intent = MyJobService.newIntent(page++)
                    jobScheduler.enqueue(jobInfo, JobWorkItem(intent))
                } else {
                    startService(MyIntentService2.newIntent(this@MainActivity,page++))
                }
            }
            btnJobIntentService.setOnClickListener {
                MyJobIntentService.enqueue(this@MainActivity,page++)
            }
        }
    }

}