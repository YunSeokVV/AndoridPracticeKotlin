package com.example.samplekotlin.repository

import androidx.lifecycle.LiveData
import com.example.samplekotlin.database.PlantDao
import com.example.samplekotlin.model.Plant
import com.orhanobut.logger.Logger

class RoomRepository(private val plantDao: PlantDao) {
    val allPlants: LiveData<List<Plant>> = plantDao.getAll()

    fun insertPlant(plant: Plant) {
        plantDao.insertPlant(plant)
    }

    //Dao 를 사용해서 DB의 데이터를 전부 LiveData형태로 갖고온다.

//    fun getAllPlants() : LiveData<List<Plant>> {
//        Logger.v("getAllPlants called")
//        // 이게 null이 된다. 왜지??
//        Logger.v(plantDao.getAll().value.toString())
//
//        return plantDao.getAll()
//    }
    //fun getAllPlants() : LiveData<List<Plant>> = plantDao.getAll()
}