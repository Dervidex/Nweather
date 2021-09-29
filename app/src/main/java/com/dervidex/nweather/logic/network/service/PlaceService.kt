package com.dervidex.nweather.logic.network.service

import com.dervidex.nweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {

    @GET("v2/place")
    fun queryPlaces(@Query("query") place: String) : Call<PlaceResponse>
}