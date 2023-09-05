package com.example.samplekotlin.util

import android.content.Context
import android.widget.Toast

class Util(context: Context) {
    private val context: Context = context
    public fun makeToastMessage(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}