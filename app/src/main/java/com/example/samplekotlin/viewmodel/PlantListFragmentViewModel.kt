package com.example.samplekotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samplekotlin.adpater.PlantListAdapter
import com.example.samplekotlin.model.Plant
import com.example.samplekotlin.model.UnsplashResults
import com.example.samplekotlin.useCase.GetRemotePlantUseCase
import kotlinx.coroutines.launch
import retrofit2.Response


class PlantListFragmentViewModel(private val getPlantUseCase: GetRemotePlantUseCase) : ViewModel() {
    private val _plants = MutableLiveData<Response<UnsplashResults>>()
    val plants: LiveData<Response<UnsplashResults>>
        get() {
            return _plants
        }

    init {
        viewModelScope.launch {
            _plants.value = getPlantUseCase.loadPlant()
        }
    }

    fun showRemotePlantData(
        response: Response<UnsplashResults>,
        plantListAdapter: PlantListAdapter
    ) {
        val loadedList = mutableListOf<Plant>()
        val resultList = response.body()?.results

        resultList?.map {
            it.user.get("location")?.let{location->
                loadedList.add(Plant(location.toString(), 0, it.urls["raw"].toString()))
            }
        }

        plantListAdapter.setOriginalData(loadedList)
        plantListAdapter.setData(loadedList)
    }
}