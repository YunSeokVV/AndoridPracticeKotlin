package com.example.samplekotlin.util

import java.util.concurrent.Executor
import java.util.concurrent.Executors

interface ExecutorInterface {

    // 4주차 과제를 진행했을때 처음 executor를 추상화 시키기 위해서 적은 코드다. 말이 안되는게 애초에 인터페이스 메소드 안에 내용을 넣는 시점부터 추상화는 망한거다.
//    fun executeApi(doThing: Any) {
//        val executor: Executor = Executors.newSingleThreadExecutor()
//        executor.execute {
//            doThing
//        }
//    }

    fun executeApi(task : () -> Unit)
}