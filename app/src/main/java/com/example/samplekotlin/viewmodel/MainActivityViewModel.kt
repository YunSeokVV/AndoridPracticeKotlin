package com.example.samplekotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.samplekotlin.model.Plant
import com.example.samplekotlin.useCase.GetLocalPlantUserCase

class MainActivityViewModel(private val getLocalPlantUserCase: GetLocalPlantUserCase) : ViewModel() {
    private val _localPlant : LiveData<List<Plant>>
    val localPlant : LiveData<List<Plant>>get() {return _localPlant}

    private val _filterVisibleLiveData = MutableLiveData<Boolean>()
    val filterVisibleLiveData : LiveData<Boolean>get(){return _filterVisibleLiveData}

    init{
        _localPlant = getLocalPlantUserCase.loadPlant()
    }

    fun setfilterVisible() {
        val currentvalue = _filterVisibleLiveData.value ?: true
        _filterVisibleLiveData.value = !currentvalue
    }


}