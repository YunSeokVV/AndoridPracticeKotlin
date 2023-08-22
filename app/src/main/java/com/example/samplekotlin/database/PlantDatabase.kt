package com.example.samplekotlin.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.samplekotlin.vo.Plant

@Database(entities = [Plant::class], version = 1)
abstract class PlantDatabase : RoomDatabase() {
    abstract fun plantDao(): PlantDao?
}