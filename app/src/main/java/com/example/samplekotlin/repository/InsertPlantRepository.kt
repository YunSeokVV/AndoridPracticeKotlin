package com.example.samplekotlin.repository

import com.example.samplekotlin.dataSource.local.InsertPlantDataSource
import com.example.samplekotlin.dataSource.local.InsertPlantDataSourceImpl
import com.example.samplekotlin.model.Plant

interface InsertPlantRepository{
    suspend fun insertPlant(plant : Plant)
}

class InsertPlantRepositoryImpl(private val insertPlantDataSource : InsertPlantDataSource) : InsertPlantRepository{
    override suspend fun insertPlant(plant: Plant) {
        insertPlantDataSource.insertData(plant)
    }


}