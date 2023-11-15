package com.example.samplekotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _isFiltered = MutableLiveData<Boolean>(false)
    val isFiltered : LiveData<Boolean>get() {return _isFiltered}

    private val originalData = mutableListOf<Plant>()

    fun setOriginalData(data: List<Plant>) {
        this.originalData.clear()
        this.originalData.addAll(data)
    }

    fun getOriginalData(): List<Plant> {

        return originalData
    }

    fun switchIsFiltred() {
        _isFiltered.value?.let{
            _isFiltered.value = !it
        }
    }

    init {
        viewModelScope.launch {
            _plants.value = getPlantUseCase.loadPlant()
        }
    }

    fun filterPlantData(originalData: List<Plant>) : List<Plant> {
        val filtered: List<Plant> = originalData.take(4).mapNotNull { it }
        return filtered
    }

    fun showRemotePlantData(
        response: Response<UnsplashResults>
    ) : List<Plant> {
        val loadedList = mutableListOf<Plant>()
        val resultList = response.body()?.results

        resultList?.map {
            it.user.get("location")?.let{location->
                loadedList.add(Plant(location.toString(), 0, it.urls["raw"].toString()))
            }
        }

        return loadedList
    }
}