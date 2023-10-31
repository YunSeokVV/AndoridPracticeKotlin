package com.example.samplekotlin.repository

import com.example.samplekotlin.BuildConfig
import com.example.samplekotlin.model.Plant
import com.example.samplekotlin.model.UnsplashResults
import com.example.samplekotlin.network.NetworkProvider
import com.example.samplekotlin.network.PlantApiService
import com.orhanobut.logger.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlantRepositry {
    private val plantApiService: PlantApiService = NetworkProvider.provideApi()

    // 보통 서버에서 받아오는 시점에 전부 dataClass 형태로 받아오던데 나의 경우는 이미지의 url 값만을 필요로 하기 때문에 이미지의 url값만 받아온다.
    fun getPlants(callback: (String) -> Unit){
//        val result = plantApiService.plantImage("Apple", BuildConfig.UNSPLASH_KEY)
//        result.enqueue(object : Callback<UnsplashResults> {
//            override fun onResponse(
//                call: Call<UnsplashResults>,
//                response: Response<UnsplashResults>
//            ) {
//                val resultList = response.body()?.results
//                resultList?.forEachIndexed { index, element ->
//                    val imgLink = response.body()?.results?.get(index)?.urls?.get("raw").toString()
//                    imgLink?.let{callback(it)}
//                }
//            }
//
//            override fun onFailure(call: Call<UnsplashResults>, t: Throwable) {
//                Logger.e("onFailure called ${t.stackTraceToString()}")
//            }
//
//        })
    }


}