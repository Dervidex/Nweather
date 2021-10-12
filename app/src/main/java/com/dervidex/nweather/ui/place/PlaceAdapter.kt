package com.dervidex.nweather.ui.place

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dervidex.nweather.databinding.ItemPlaceBinding
import com.dervidex.nweather.logic.Repository
import com.dervidex.nweather.logic.model.Place
import com.dervidex.nweather.ui.weather.WeatherActivity

class PlaceAdapter(private val fragment: PlaceFragment, private val placeList: List<Place>) : RecyclerView.Adapter<PlaceAdapter.ViewHolder>(){
    inner class ViewHolder(binding: ItemPlaceBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvPlaceName = binding.tvPlaceName
        val tvPlaceAddress = binding.tvPlaceAddress
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ViewHolder(binding)
        holder.itemView.setOnClickListener {
            placeList[holder.adapterPosition].apply {
                // 保存place信息到本地
                // mvvm框架结构上ui控制层只能对viewModel操作，不能直接对Repository操作
                fragment.viewModel.savedPlace(this)

                WeatherActivity.startAction(fragment, name, location.lng, location.lat)
            }

        }
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