package com.example.samplekotlin.util

import android.content.Context
import android.widget.Toast

class CompanionUtil {

    // 아래와 같은 방법으로 companion object를 활용할수도 있습니다. 하지만 굳이 이렇게 하면 companion object를 사용해야 할 필요가 없다고 느꼈습니다.
    //companion object를 사용하면 싱글톤에서 object와 다르게 생성자를 설정할 수 있는 것으로 공부했습니다.
//    companion object makeToastMessage{
//        fun makeToastMessage(context : Context, msg : String){
//            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
//        }
//    }

    companion object {
        private val instance = CompanionUtil()
        // todo : by lazy를 사용해서 늦은 초기화를 하려고 했는데 애초에 by lazy가 어떤 값을 쓸 때 instance의 속성값을 변경해주는 역할을 하는건데... 굳이 by lazy를 쓸 필요는 없을듯
//        private val instance by lazy{
//
//        }
        //todo : GPT나 예제들을 보면 전부 instance를 var로 설정해서 아래쪽에 조건문을 달고 instance가 비었는지 안비었는지 확인하는 절차를 가지던데 그냥 나처럼 val 로 설정하면 안되나?
        //private var instance: CompanionUtil? = null

        fun getInstance() : CompanionUtil {
            return instance
        }

    }

    fun makeToastMessage(context : Context, msg : String){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}