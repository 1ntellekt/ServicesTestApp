package com.example.servicestestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.servicestestapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding:ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.apply {
            btnService.setOnClickListener {
                startService(MyService.newIntent(this@MainActivity))
            }
        }
    }
}