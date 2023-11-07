package com.example.samplekotlin.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samplekotlin.database.PlantDao
import com.example.samplekotlin.database.PlantDao_Impl
import com.example.samplekotlin.database.PlantDatabase
import com.example.samplekotlin.model.Plant
import com.example.samplekotlin.repository.GetLocalPlantRepositoryImpl
import com.example.samplekotlin.useCase.GetLocalPlantUseCaseImpl
import com.example.samplekotlin.useCase.GetLocalPlantUserCase
import com.orhanobut.logger.Logger
import kotlinx.coroutines.launch

class MainActivityViewModel(private val getLocalPlantUserCase: GetLocalPlantUserCase) : ViewModel() {

    lateinit var localPlant : LiveData<List<Plant>>
    private var filterVisibleLiveData = MutableLiveData<Boolean>()

    init{
        loadLocalPlant()
    }

    fun setfilterVisible() {
        val currentvalue = filterVisibleLiveData.value ?: true
        filterVisibleLiveData.value = !currentvalue
    }

    fun getfilterVisibleLiveData(): MutableLiveData<Boolean> {

        return filterVisibleLiveData
    }

    fun loadLocalPlant() {

        localPlant = getLocalPlantUserCase.loadPlant()
    }

    @JvmName("callFromPlant")
    fun getLocalPlant() : LiveData<List<Plant>>{
        return localPlant
    }


}