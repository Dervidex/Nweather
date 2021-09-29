package com.dervidex.nweather.logic.model

import com.google.gson.annotations.SerializedName

data class PlaceResponse(val status: String, val places: List<Place>)

data class Place(
    val name: String,
    val location: Location,
    @SerializedName("formatted_address") val address: String)

/**
 * @param lng 经度
 * @param lat 纬度
 */
data class Location(val lng: String, val lat: String)
