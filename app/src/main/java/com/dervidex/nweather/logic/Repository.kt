package com.dervidex.nweather.logic

import androidx.lifecycle.liveData
import com.dervidex.nweather.logic.network.AppNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

object Repository {

    fun queryPlaces(place: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = AppNetwork.queryPlaces(place)
            if (placeResponse.status == "ok") {
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