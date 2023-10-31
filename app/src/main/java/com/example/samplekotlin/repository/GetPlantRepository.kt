package com.example.samplekotlin.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.samplekotlin.dataSource.local.GetLocalPlantDataSource
import com.example.samplekotlin.dataSource.remote.PlantURLDataSource
import com.example.samplekotlin.model.ImageURL
import com.example.samplekotlin.model.Plant
import com.example.samplekotlin.model.UnsplashResults
import retrofit2.Response

interface GetRemotePlantRepository {
    suspend fun getPlant() : Response<UnsplashResults>
}

interface GetLocalPlantRepository{
    suspend fun getPlant() : LiveData<List<Plant>>
}

class GetRemotePlantRepositoryImpl(private val plantURLDataSource: PlantURLDataSource) : GetRemotePlantRepository{
    override suspend fun getPlant(): Response<UnsplashResults> {
        return plantURLDataSource.getPlantURL()
    }

}

class GetLocalPlantRepositoryImpl(private val plantDataSource : GetLocalPlantDataSource) : GetLocalPlantRepository{
    override suspend fun getPlant(): LiveData<List<Plant>> {
        return plantDataSource.getPlant()
    }
}