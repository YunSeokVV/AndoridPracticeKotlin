package com.example.samplekotlin.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyGardenFramgnetViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyGardenFragmentViewModel::class.java)) {
            return MyGardenFragmentViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}