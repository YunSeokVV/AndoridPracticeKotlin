package com.example.samplekotlin.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.samplekotlin.vo.Plant

@Dao
interface PlantDao {
    @Insert
    fun insert(plant : Plant);

    @Query("SELECT * FROM plant")
    fun getFavoritePlant() : List<Plant>;
}