package com.example.samplekotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samplekotlin.model.ImageURL
import com.example.samplekotlin.model.UnsplashResults
import com.example.samplekotlin.repository.PlantRepositry
import com.example.samplekotlin.useCase.GetRemotePlantUseCase
import com.orhanobut.logger.Logger
import kotlinx.coroutines.launch
import retrofit2.Response


class PlantListFragmentViewModel(private val getPlantUseCase: GetRemotePlantUseCase) : ViewModel(){
    private val plants = MutableLiveData<Response<UnsplashResults>>()

    init{
        getdPlants()
    }

    private fun getdPlants(){
        viewModelScope.launch {
            plants.value = getPlantUseCase.loadPlant()
        }
    }

    fun getPlants() : MutableLiveData<Response<UnsplashResults>>{

        return plants
    }
}