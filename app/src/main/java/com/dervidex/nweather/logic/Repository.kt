package com.dervidex.nweather.logic

import androidx.lifecycle.liveData
import com.dervidex.nweather.logic.model.Place
import com.dervidex.nweather.logic.network.AppNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException

object Repository {

    fun queryPlaces(place: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = AppNetwork.queryPlaces(place)
            if (placeResponse.status == "OK") {
                placeResponse.places.let {
                    Result.success(it)
                }
            } else {
                Result.failure(RuntimeException("response status ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
        emit(result)
    }
}