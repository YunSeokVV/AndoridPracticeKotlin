package com.example.samplekotlin.useCase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.samplekotlin.model.ImageURL
import com.example.samplekotlin.model.Plant
import com.example.samplekotlin.model.UnsplashResults
import com.example.samplekotlin.repository.GetLocalPlantRepository
import com.example.samplekotlin.repository.GetRemotePlantRepository
import retrofit2.Response

interface GetLocalPlantUserCase{
    fun loadPlant() : LiveData<List<Plant>>
}

interface GetRemotePlantUseCase {
    suspend fun loadPlant() : Response<UnsplashResults>
}

class GetRemotePlantUseCaseImpl(private val getRemotePlantRepository: GetRemotePlantRepository) : GetRemotePlantUseCase {
    override suspend fun loadPlant(): Response<UnsplashResults> {
        return getRemotePlantRepository.getPlant()
    }

}

class GetLocalPlantUseCaseImpl(private val getLocalPlantRepository : GetLocalPlantRepository) : GetLocalPlantUserCase{
    override fun loadPlant(): LiveData<List<Plant>> {
        return getLocalPlantRepository.getPlant()
    }

}