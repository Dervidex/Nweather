package com.dervidex.nweather.ui.weather

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dervidex.nweather.R
import com.dervidex.nweather.databinding.ActivityWeatherBinding
import com.dervidex.nweather.databinding.ForecastItemBinding
import com.dervidex.nweather.logic.model.DailyResponse
import com.dervidex.nweather.logic.model.RealtimeResponse
import com.dervidex.nweather.logic.model.Sky
import com.dervidex.nweather.logic.model.Weather
import org.neko.util.Log2
import org.neko.util.showToast
import org.neko.util.startActivity
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity() {

    companion object {
        fun startAction(fragment: Fragment, placeName: String, locationLng: String, locationLat: String) {
            startActivity<WeatherActivity>(fragment) {
                putExtra("placeName", placeName)
                putExtra("locationLng", locationLng)
                putExtra("locationLat", locationLat)
            }
        }
    }

    private lateinit var binding: ActivityWeatherBinding
    private val viewModel by lazy { ViewModelProvider(this).get(WeatherViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideStatusBar()

        viewModel.placeName = intent.getStringExtra("placeName") ?: ""
        viewModel.locationLng = intent.getStringExtra("locationLng") ?: ""
        viewModel.locationLat = intent.getStringExtra("locationLat") ?: ""


        viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat)

        binding.root.setOnRefreshListener {
            viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat)
        }

        viewModel.weatherLiveData.observe(this) {
            val weather = it.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
                binding.root.isRefreshing = false
                Log2.d("weather", "get newest weather info")
            } else {
                it.exceptionOrNull()?.printStackTrace()
                "无法获取天气信息".showToast(this)
            }
        }
    }

    /**
     * 填充布局
     */
    private fun showWeatherInfo(weather: Weather) {
        val realTime = weather.realtimeResponse.result.realtime
        val daily = weather.dailyResponse.result.daily

        initNow(realTime)
        initForecast(daily)
        initLifeIndex(daily)

        binding.weatherLayout.visibility = View.VISIBLE
    }

    /**
     * 填充now布局
     */
    private fun initNow(realTime: RealtimeResponse.Realtime) {
        binding.nowLayout.tvPlaceName.text = viewModel.placeName

        binding.nowLayout.tvCurrentTemp.text = resources.getString(R.string.temperature)
            .format(realTime.temperature.toInt())


        binding.nowLayout.tvCurrentSky.text = Sky.getSky(realTime.skycon).info

        binding.nowLayout.tvCurrentAQI.text = String.format(resources.getString(R.string.airQuality),
            realTime.airQuality.aqi.chn.toInt())

        binding.nowLayout.root.setBackgroundResource(Sky.getSky(realTime.skycon).bg)
    }

    /**
     * 填充forecast布局
     */
    private fun initForecast(daily: DailyResponse.Daily) {
        binding.forecastLayout.forecastItemContainer.removeAllViews()

        // 遍历，预报信息封装到forecastItem
        // 将forecastItem布局一项项添加到forecastLayout
        // 各List大小相同，随便取出一个
        for (i in daily.skycon.indices) {
            val itemBinding = ForecastItemBinding.inflate(layoutInflater)
            val skycon = daily.skycon[i]
            val temperature = daily.temperature[i]

            itemBinding.tvDate.text = skycon.date.let {
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it)
            }
            itemBinding.ivSkyIcon.setImageResource(Sky.getSky(skycon.value).icon)

            itemBinding.tvSkyInfo.text = Sky.getSky(skycon.value).info

            itemBinding.tvTemperature.text = resources.getString(R.string.temperatureRange)
                .format(temperature.min.toInt(), temperature.max.toInt())

            // 添加
            binding.forecastLayout.forecastItemContainer.addView(itemBinding.root)
        }
    }

    /**
     * 填充lifeIndex布局
     */
    private fun initLifeIndex(daily: DailyResponse.Daily) {
        val lifeIndex = daily.lifeIndex

        binding.lifeIndexLayout.tvColdText.text = lifeIndex.coldRisk[0].desc
        binding.lifeIndexLayout.tvDressing.text = lifeIndex.dressing[0].desc
        binding.lifeIndexLayout.tvUltraviolet.text = lifeIndex.ultraviolet[0].desc
        binding.lifeIndexLayout.tvCarWashing.text = lifeIndex.carWashing[0].desc
    }

    private fun hideStatusBar() {
        // WindowInsetsController在api30(android 11)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        window.statusBarColor = Color.TRANSPARENT
    }
}