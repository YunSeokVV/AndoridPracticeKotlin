package com.example.samplekotlin.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import com.example.samplekotlin.model.Plant

@Dao
interface PlantDao {
    @Query("SELECT * FROM Plant")
    fun getAll(): LiveData<List<Plant>>

    //@Insert(onConflict = OnConflictStrategy.IGNORE)
    @Insert
    suspend fun insertPlant(plant: Plant)
}