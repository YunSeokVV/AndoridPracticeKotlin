package com.example.samplekotlin.vo

import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap

data class URLs(
    @field:SerializedName("urls")
    val urls: LinkedTreeMap<String, Any>

)