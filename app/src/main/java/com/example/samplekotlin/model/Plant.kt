package com.example.samplekotlin.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Plant(
    val name: String,
    val waterPeriod: Int,
    val imageResource:
    String
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}