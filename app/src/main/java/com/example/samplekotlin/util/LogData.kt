package com.example.samplekotlin.util

import android.util.Log

class LogData {
    companion object {
        fun logData(tag : String, variable : String, message: String){
            Log.v(tag, "variable is $variable")
            Log.v(tag, message)
        }
    }
}