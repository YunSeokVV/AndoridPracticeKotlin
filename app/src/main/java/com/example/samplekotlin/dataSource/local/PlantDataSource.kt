package com.example.samplekotlin.dataSource.local

import androidx.lifecycle.LiveData
import com.example.samplekotlin.model.Plant

interface InsertPlantDataSource {
    suspend fun insertData(plant : Plant)
}

interface GetLocalPlantDataSource {
    fun getPlant() : LiveData<List<Plant>>
}
