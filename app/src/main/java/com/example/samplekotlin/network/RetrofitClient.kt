package com.example.samplekotlin.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.converter.scalars.ScalarsConverterFactory

// 코틀린에서 object 예약어를 사용하면 클래스를 정의하는 동시에 객체를 생성하는게 가능하다.
object RetrofitClient {
    val gson : Gson = GsonBuilder().setLenient().create()
    private const val BASE_URL = "https://en.wikipedia.org/wiki/"
    private val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
//        .addConverterFactory(ScalarsConverterFactory.create())
        //.addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    val apiService : WikiApiService = retrofit.create(WikiApiService::class.java)

}