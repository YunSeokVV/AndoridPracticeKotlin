package com.example.samplekotlin.vo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Plant(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val waterPeriod: Int,
    val imageResource:
    String
) : Serializable {}

