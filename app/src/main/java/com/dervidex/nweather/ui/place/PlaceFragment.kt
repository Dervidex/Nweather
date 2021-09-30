package com.dervidex.nweather.ui.place

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dervidex.nweather.AppApplication
import com.dervidex.nweather.databinding.FragmentPlaceBinding
import org.neko.util.showToast

class PlaceFragment : Fragment() {
    private var _binding: FragmentPlaceBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java) }
    private lateinit var adapter: PlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.rvPlaces.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter= PlaceAdapter(viewModel.placeList)
        binding.rvPlaces.adapter = adapter


        searchViewLiveUpdate()

        callbackPlacesLiveData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * 根据搜索框内容实时更新位置信息
     */
    private fun searchViewLiveUpdate() {
        binding.etSearch.addTextChangedListener {
            val content = it.toString()
            if (content.isNotEmpty()) {
                viewModel.queryPlace(content)
            } else {
                viewModel.placeList.clear()
                for (i in viewModel.placeList.indices) {
                    adapter.notifyItemRemoved(i)
                }
                binding.rvPlaces.visibility = View.GONE
                binding.ivBackground.visibility = View.VISIBLE
            }
        }
    }

    /**
     * 响应PlacesLiveData
     */
    @SuppressLint("NotifyDataSetChanged")
    private fun callbackPlacesLiveData() {
        viewModel.placesLiveData.observe(viewLifecycleOwner) {
            if (binding.etSearch.text.toString() == "") {
                return@observe
            }

            val placeList = it.getOrNull()
            if (placeList != null) {

                viewModel.placeList.clear()
                viewModel.placeList.addAll(placeList)

                adapter.notifyDataSetChanged()

                binding.rvPlaces.visibility = View.VISIBLE
                binding.ivBackground.visibility = View.GONE
            } else {
                "查询地址为空".showToast(AppApplication.context)
                // 打印异常
                it.exceptionOrNull()?.printStackTrace()
            }
        }
    }
}