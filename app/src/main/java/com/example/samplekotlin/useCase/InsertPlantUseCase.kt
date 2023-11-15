package com.example.samplekotlin.useCase

import com.example.samplekotlin.model.Plant
import com.example.samplekotlin.repository.InsertPlantRepository

interface InsertPlantUseCase {
    suspend fun insertPlant(plant : Plant)
}

class InsertPlantUseCaseImpl(private val insertPlantRepository: InsertPlantRepository) : InsertPlantUseCase{
    override suspend fun insertPlant(plant: Plant) {
        insertPlantRepository.insertPlant(plant)
    }


}