package com.example.samplekotlin.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Plant(
    val location: String,
    val waterPeriod: Int,
    @PrimaryKey
    val imageResource:
    String
) : Serializable