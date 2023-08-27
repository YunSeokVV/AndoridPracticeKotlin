package com.example.samplekotlin.database

import android.content.Context
import androidx.room.Room

object DatabaseClient {
    private var plantDatabase: PlantDatabase? = null
    private const val DATABSE_NAME = "plant_database"
    @Synchronized
    fun getPlantDatabase(context: Context?): PlantDatabase? {
        if (plantDatabase == null) {
            plantDatabase = Room.databaseBuilder(
                context!!,
                PlantDatabase::class.java, DATABSE_NAME
            ).build()
        }
        return plantDatabase
    }
}
