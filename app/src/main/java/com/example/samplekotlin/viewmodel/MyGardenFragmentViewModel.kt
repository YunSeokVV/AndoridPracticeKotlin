package com.example.samplekotlin.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.samplekotlin.database.PlantDatabase
import com.example.samplekotlin.model.Plant
import com.example.samplekotlin.repository.RoomRepository
import com.orhanobut.logger.Logger

class MyGardenFragmentViewModel(private val context: Context) : ViewModel() {
//    val dao = PlantDatabase.getInstance(context).plantDao()


//    //DB에서 불러온 데이터를 관찰하는 LiveData가 필요하다.
//    val allPlants: LiveData<List<Plant>> = repository.allPlants
//
//    fun addPlant(plant: Plant) {
//        Logger.v("addPlant called")
//        repository.insertPlant(plant)
//    }


    //fun getAllPlants() : LiveData<List<Plant>> = allPlants
}  