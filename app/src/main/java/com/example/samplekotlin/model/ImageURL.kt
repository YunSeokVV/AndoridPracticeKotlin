package com.example.samplekotlin.model

import com.google.gson.internal.LinkedTreeMap

data class ImageURL(
    //@field:SerializedName("urls")
    val urls: LinkedTreeMap<String, Any>,
    val user: LinkedTreeMap<String, Any>
)