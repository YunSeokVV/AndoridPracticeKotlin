package com.example.samplekotlin.network

import com.example.samplekotlin.vo.URLs
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PlantApiService {
    @GET("photos/{plantName}?client_id=ikqQlp0x6DZVrwA6ROTIqCq4zXuv4TWgMKNKHT-b1fY")
    fun plantImage(@Path("plantName") plantName: String?): Call<URLs>


}