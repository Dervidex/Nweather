package com.dervidex.nweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.dervidex.nweather.databinding.ActivityMainBinding
import com.dervidex.nweather.logic.network.AppNetwork
import com.dervidex.nweather.ui.place.PlaceViewModel
import kotlinx.coroutines.*
import org.neko.util.Log2
import org.neko.util.log2D

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}