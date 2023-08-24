package com.example.samplekotlin.vo

import java.io.Serializable

data class Plant(val id : Long = 0, val name : String, val waterPeriod : Int, val imageResource : String): Serializable{

}

