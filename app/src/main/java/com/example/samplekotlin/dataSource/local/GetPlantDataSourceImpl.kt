package com.example.samplekotlin.dataSource.local

import androidx.lifecycle.LiveData
import com.example.samplekotlin.database.PlantDao
import com.example.samplekotlin.model.Plant

class GetPlantDataSourceImpl(private val plantDao : PlantDao) : GetLocalPlantDataSource {
    override fun getPlant(): LiveData<List<Plant>> {
        return plantDao.getAll()
    }
}