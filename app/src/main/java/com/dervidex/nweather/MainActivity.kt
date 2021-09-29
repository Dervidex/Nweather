package com.dervidex.nweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.liveData
import com.dervidex.nweather.databinding.ActivityMainBinding
import com.dervidex.nweather.logic.network.AppNetwork
import kotlinx.coroutines.*
import org.neko.util.log2D

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}