package com.dervidex.nweather.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.dervidex.nweather.AppApplication
import com.dervidex.nweather.logic.model.Place
import com.google.gson.Gson

object PlaceDao {
    private val sp by lazy {
        AppApplication.context.getSharedPreferences(
            "NWeather",
            Context.MODE_PRIVATE
        )
    }

    fun savePlace(place: Place) = sp.edit {
        putString("place", Gson().toJson(place))
    }


    fun getPlace(): Place = sp.getString("place", "").let {
        Gson().fromJson(it, Place::class.java)
    }

    fun isPlaceSaved() = sp.contains("place")
}