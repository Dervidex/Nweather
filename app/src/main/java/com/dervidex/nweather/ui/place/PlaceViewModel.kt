package com.dervidex.nweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dervidex.nweather.logic.Repository

class PlaceViewModel : ViewModel() {
    private val placeLiveData = MutableLiveData<String>()

    val placesLiveData = Transformations.switchMap(placeLiveData) {
        Repository.queryPlaces(it)
    }

    fun queryPlace(place: String) {
        placeLiveData.value = place
    }
}