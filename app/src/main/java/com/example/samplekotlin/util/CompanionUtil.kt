package com.example.samplekotlin.util

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.example.samplekotlin.model.Plant

class CompanionUtil {

    companion object {
        fun likedPlant(imgURL : String, localPlant : LiveData<List<Plant>>) : Int{
//            var isVisible : Int = View.VISIBLE
//            localPlant.value?.forEach {
//                if(it.imageResource.equals(imgURL)){
//                    Logger.v("GONE")
//                    isVisible = View.GONE
//                }
//            }
//            return isVisible

            return if (localPlant.value?.find { it. imageResource == imgURL } != null) View.GONE else View.VISIBLE
        }

        fun makeToastMessage(context: Context, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

    }
}