package com.example.samplekotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {
    private var filterVisibleLiveData = MutableLiveData<Boolean>()

    fun setfilterVisible() {
        val currentvalue = filterVisibleLiveData.value ?: true
        filterVisibleLiveData.value = !currentvalue
    }

    fun getfilterVisibleLiveData(): MutableLiveData<Boolean> {

        return filterVisibleLiveData
    }
}