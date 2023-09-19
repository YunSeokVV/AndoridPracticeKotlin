package com.example.samplekotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.samplekotlin.model.Plant

class PlantDetailFragmentViewModel : ViewModel() {

    // 이 녀석은 나중에 없애야 하는 변수다. MyGardenFragment의 liveData가 어차피 사용자가 좋아요 누른 데이터를 다 갖고 있으니까. (굳이 중복되는 값을 가진 라이브데이터를 만들필요x)
    val likeData = MutableLiveData<List<Plant>>()
    fun setLikeData(list: List<Plant>) {
        likeData.value = list
    }

    val plant = MutableLiveData<Plant>()
    fun setPlant(plant: Plant) {
        this.plant.value = plant
    }

}