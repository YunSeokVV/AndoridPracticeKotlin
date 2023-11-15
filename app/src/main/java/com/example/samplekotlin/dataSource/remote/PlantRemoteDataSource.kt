package com.example.samplekotlin.dataSource.remote

import com.example.samplekotlin.BuildConfig
import com.example.samplekotlin.model.ImageURL
import com.example.samplekotlin.model.UnsplashResults
import com.example.samplekotlin.network.PlantApiService
import retrofit2.Response

class PlantRemoteDataSource(private val plantApiService: PlantApiService) : PlantURLDataSource {

    override suspend fun getPlantURL(): Response<UnsplashResults> {
        return plantApiService.plantImage("Apple", BuildConfig.UNSPLASH_KEY)
    }
}