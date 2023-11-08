package com.example.samplekotlin.util

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.example.samplekotlin.model.Plant
import com.orhanobut.logger.Logger

class CompanionUtil {

    // 아래와 같은 방법으로 companion object를 활용할수도 있습니다. 하지만 굳이 이렇게 하면 companion object를 사용해야 할 필요가 없다고 느꼈습니다.
    //companion object를 사용하면 싱글톤에서 object와 다르게 생성자를 설정할 수 있는 것으로 공부했습니다.
//    companion object makeToastMessage{
//        fun makeToastMessage(context : Context, msg : String){
//            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
//        }
//    }

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
    }

    fun makeToastMessage(context : Context, msg : String){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}