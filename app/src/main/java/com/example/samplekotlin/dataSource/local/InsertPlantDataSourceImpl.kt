package com.example.samplekotlin.dataSource.local

import com.example.samplekotlin.database.PlantDao
import com.example.samplekotlin.model.Plant

class InsertPlantDataSourceImpl(private val plantDao: PlantDao) : InsertPlantDataSource{
    override suspend fun insertData(plant: Plant) {
        plantDao.insertPlant(plant)
    }
}