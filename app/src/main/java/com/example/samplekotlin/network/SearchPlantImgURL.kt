package com.example.samplekotlin.network

import com.example.samplekotlin.vo.URLs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
// todo : Retrofit 통신 코드를 중복시키고 싶지 않아서 따로 클래스를 만들어서 관리하려고 했다. 하지만 통신 결과를 onResponse 에서 확인할 수 있는데 그 결과를 통신을 처음 호출했었던 곳으로 돌아갈 방법을 모르겠다. 이럴때 사용하는게 코루틴이라고 GPT가 대답을 해주긴 했는데... 일단 넘어가는걸로

class SearchPlantImgURL (val plantID : String){
    val retrofit = Retrofit.Builder().baseUrl("https://api.unsplash.com/").addConverterFactory(
        GsonConverterFactory.create()).build()
    val apiService : PlantApiService = retrofit.create(PlantApiService::class.java)
    val repos : Call<URLs> = apiService.plantImage(plantID)


    fun imgURL(plnatURL : String) : String {

        return plnatURL
    }


    fun coonnectRetrofit(){
        repos.enqueue(object : Callback<URLs> {
            override fun onResponse(call: Call<URLs>, response: Response<URLs>) {
                if(response.isSuccessful()) {
                    //LinkedTreeMap임.
                    //println("${response.body()?.urls!!::class.simpleName}")


                    imgURL(response.body()?.urls!!.get("raw").toString())
                } else { // code == 400

                }

            }

            override fun onFailure(call: Call<URLs>, t: Throwable) {

            }
        })
    }

}