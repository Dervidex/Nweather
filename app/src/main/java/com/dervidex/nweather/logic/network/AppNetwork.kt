package com.dervidex.nweather.logic.network

import com.dervidex.nweather.logic.network.service.PlaceService
import com.dervidex.nweather.logic.network.service.WeatherService
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 统一的网络访问接口
 */
object AppNetwork {

    private val placeService by lazy { ServiceCreator.create<PlaceService>() }
    private val weatherService by lazy { ServiceCreator.create<WeatherService>() }

    suspend fun queryPlaces(place: String) = placeService.queryPlaces(place).await()

    suspend fun getRealtimeWeather(lng: String, lat: String) =
        weatherService.getRealtimeWeather(lng, lat).await()

    suspend fun getDailyWeather(lng: String, lat: String) =
        weatherService.getDailyWeather(lng, lat).await()


    private object ServiceCreator {
        private const val BASE_URL = "https://api.caiyunapp.com/"

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

        inline fun <reified T> create(): T = create(T::class.java)
    }
}