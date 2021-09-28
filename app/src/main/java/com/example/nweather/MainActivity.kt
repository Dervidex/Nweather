package com.example.nweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nweather.service.Test
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.neko.util.Log2
import org.neko.util.log2D
import org.neko.util.transformFullUnicode
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var retrofit: Retrofit
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retrofit = Retrofit.Builder()
            .baseUrl("https://api.caiyunapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val testService = retrofit.create(Test::class.java)

        CoroutineScope(Dispatchers.Main).launch {
            testService.getAreaStatus("北京").await().string().let {
                transformFullUnicode(it).log2D("unicode")
            }

            testService.getRealTime("116.322056,39.89491").await().string().let {
                transformFullUnicode(it).log2D("unicode")
            }

            testService.getDaily("116.322056,39.89491").await().string().let {
                transformFullUnicode(it).log2D("unicode")
            }
        }
    }
}