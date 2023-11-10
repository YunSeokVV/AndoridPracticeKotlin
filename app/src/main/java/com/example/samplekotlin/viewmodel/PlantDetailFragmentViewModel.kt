package com.example.samplekotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samplekotlin.model.Plant
import com.example.samplekotlin.useCase.InsertPlantUseCase
import kotlinx.coroutines.launch

class PlantDetailFragmentViewModel(private val insertPlantUseCase: InsertPlantUseCase) : ViewModel() {
    fun insertPlant(plant : Plant) = viewModelScope.launch{
        insertPlantUseCase.insertPlant(plant)
    }

}