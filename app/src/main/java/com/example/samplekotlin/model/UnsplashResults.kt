package com.example.samplekotlin.model

import com.google.gson.annotations.SerializedName

data class UnsplashResults(
    @field:SerializedName("results")
    val results: List<ImageURL>

)
