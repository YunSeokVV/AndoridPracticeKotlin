package com.example.samplekotlin.util

import com.example.samplekotlin.model.Plant

// Fragment에서 MainActivity 로 데이터를 전달하는 인터페이스
interface SendPlantListener {
    fun sendMessage(plant : Plant)


}