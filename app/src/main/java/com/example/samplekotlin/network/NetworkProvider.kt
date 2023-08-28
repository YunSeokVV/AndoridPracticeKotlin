package com.example.samplekotlin.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object NetworkProvider {

    fun provideApi(): PlantApiService{
        return Retrofit.Builder()
            .baseUrl("https://api.unsplash.com/photos/")
            // Retrofit에서 http 요청헤더를 설정하는 방법은 두가지 인것으로 확인했다. OkHTTP 를 활용해서 요청헤더를 설정하는 방법과 레트로핏의 어노테이션을 활용하는방법.
            //이번에는 후자의 방법으로 구현을 해보고자 한다.
            //.client(OKhttp Interceptor)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PlantApiService::class.java)

    }
}