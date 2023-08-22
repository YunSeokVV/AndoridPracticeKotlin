package com.example.samplekotlin.network

import retrofit2.Call
import retrofit2.http.GET

interface WikiApiService {

    // 응답을 파싱할 데이터 클래스가 원래 들어가야 하지만 위키백과에서 문자열값만 필요로 해서 String으로 넣었다.
    @GET("Apple")
    fun getData(): Call<String>
}