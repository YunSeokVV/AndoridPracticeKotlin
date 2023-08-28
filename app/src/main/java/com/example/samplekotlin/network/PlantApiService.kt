package com.example.samplekotlin.network

import com.example.samplekotlin.vo.URLs
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface PlantApiService {
    // http 요청을 보낼때 json값으로 응답받기 위해서 요청해더를 설정해준다.
    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("{plantName}?")
    fun plantImage(
        //@Path 어노테이션은 API의 엔드포인트 경로의 일부를 동적으로 표현할때 사용하는 어노테이션이다.
        @Path("plantName") plantName: String?,
        //@Query 어노테이션은 요청값을 보낼때 쿼리 값을 넣기 위한 어노테이션이다.
        @Query("client_id", encoded = true) clinetID : String,
    ) : Call<URLs>
}