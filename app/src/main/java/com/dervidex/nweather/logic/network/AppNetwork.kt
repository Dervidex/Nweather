package com.dervidex.nweather.logic.network

import com.dervidex.nweather.logic.network.service.PlaceService
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 统一的网络访问接口
 */
object AppNetwork {

    private val placeService by lazy { ServiceCreator.create<PlaceService>() }

    suspend fun queryPlaces(place: String) = placeService.queryPlaces(place).await()




    object ServiceCreator {
        private const val BASE_URL = "https://api.caiyunapp.com/"

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

        inline fun <reified T> create(): T = create(T::class.java)
    }
}