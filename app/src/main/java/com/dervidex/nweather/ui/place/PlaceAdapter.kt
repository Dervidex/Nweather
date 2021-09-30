package com.dervidex.nweather.ui.place

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dervidex.nweather.databinding.ItemPlaceBinding
import com.dervidex.nweather.logic.model.Place

class PlaceAdapter(private val placeList: List<Place>) : RecyclerView.Adapter<PlaceAdapter.ViewHolder>(){
    inner class ViewHolder(binding: ItemPlaceBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvPlaceName = binding.tvPlaceName
        val tvPlaceAddress = binding.tvPlaceAddress
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ViewHolder(binding)

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvPlaceName.text = placeList[position].name
        holder.tvPlaceAddress.text = placeList[position].address
    }

    override fun getItemCount(): Int {
        return placeList.size
    }
}