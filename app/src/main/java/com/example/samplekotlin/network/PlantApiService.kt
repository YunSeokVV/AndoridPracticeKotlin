package com.example.samplekotlin.network

import com.example.samplekotlin.model.UnsplashResults
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface PlantApiService {
    // http 요청을 보낼때 json값으로 응답받기 위해서 요청해더를 설정해준다.
    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("photos?")
    fun plantImage(
        //@Query 어노테이션은 요청값을 보낼때 쿼리 값을 넣기 위한 어노테이션이다.
        @Query("query", encoded = true) searchTerms: String,
        @Query("client_id", encoded = true) clinetID: String
    ): Call<UnsplashResults>

}