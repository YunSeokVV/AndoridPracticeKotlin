package com.example.samplekotlin.util

import android.content.Context
import android.widget.Toast

class Util(private val context: Context) {
    public fun makeToastMessage(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    object objectUtil {
        fun makeToastMessage(context: Context, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun makeToastMessage(context: Context, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }
}