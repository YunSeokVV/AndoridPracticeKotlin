package com.example.samplekotlin.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.samplekotlin.model.Plant

@Dao
interface PlantDao {
    @Query("SELECT * FROM Plant")
    fun getAll(): List<Plant>

    @Insert
    fun insertPlant(plant: Plant)
}