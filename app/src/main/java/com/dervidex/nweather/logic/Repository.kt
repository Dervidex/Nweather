package com.dervidex.nweather.logic

import androidx.lifecycle.liveData
import com.dervidex.nweather.logic.dao.PlaceDao
import com.dervidex.nweather.logic.model.Place
import com.dervidex.nweather.logic.model.Weather
import com.dervidex.nweather.logic.network.AppNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.lang.RuntimeException

object Repository {

    fun queryPlaces(place: String) = tryCatch {
        val placeResponse = AppNetwork.queryPlaces(place)
        if (placeResponse.status == "ok") {
            placeResponse.places.let {
                Result.success(it)
            }
        } else {
            Result.failure(RuntimeException("response status ${placeResponse.status}"))
        }
    }

    fun refreshWeather(lng: String, lat: String) = tryCatch {
        coroutineScope {
            val deferredRealtime = async { AppNetwork.getRealtimeWeather(lng, lat) }
            val deferredDaily = async { AppNetwork.getDailyWeather(lng, lat) }

            // 并发
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()

            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather = Weather(realtimeResponse, dailyResponse)

                Result.success(weather)
            } else {
                Result.failure(
                    RuntimeException(
                        "realtime status is ${realtimeResponse.status}\n" +
                                "daily status is ${dailyResponse.status}"
                    )
                )
            }
        }
    }

    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getPlace(): Place = PlaceDao.getPlace()

    fun isPlaceSave() = PlaceDao.isPlaceSaved()

    private fun <T> tryCatch(block: suspend () -> Result<T>) = liveData(Dispatchers.IO) {
        val result = try {
            block()
        } catch (e: Exception) {
            Result.failure(e)
        }
        emit(result)
    }
}

