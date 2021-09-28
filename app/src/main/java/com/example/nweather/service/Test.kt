package com.example.nweather.service

import androidx.annotation.StringDef
import com.example.nweather.util.Token
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Test {
    @GET("/v2/place")
    fun getAreaStatus(@Query("query") area: String): Call<ResponseBody>

    @GET("/v2.5/$Token/{location}/realtime.json")
    fun getRealTime(@Path("location") location: String): Call<ResponseBody>

    @GET("/v2.5/$Token/{location}/daily.json")
    fun getDaily(@Path("location") location: String): Call<ResponseBody>
}