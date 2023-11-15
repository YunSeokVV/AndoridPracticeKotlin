package com.example.samplekotlin.dataSource.remote

import com.example.samplekotlin.model.ImageURL
import com.example.samplekotlin.model.Plant
import com.example.samplekotlin.model.UnsplashResults
import retrofit2.Response

interface PlantURLDataSource {
    suspend fun getPlantURL() : Response<UnsplashResults>
}

