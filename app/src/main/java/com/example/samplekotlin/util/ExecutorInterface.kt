package com.example.samplekotlin.util

import com.example.samplekotlin.database.PlantDatabase
import java.util.concurrent.Executor
import java.util.concurrent.Executors

// 처음 executor를 추상화 해보기 위해서 만들었던 인터페이스입니다.
//뷰 단에서 이 인터페이스를 실행시키고 매개변수만 넘겨줘서 할 일을 넘겨주면 DB에 데이터를 저장하거나 레트로핏을 사용해서 통신을 하는게 가능할 줄 알았는데 안됐습니다.
interface ExecutorInterface {

    fun executeApi(doThing: Any) {
        val executor: Executor = Executors.newSingleThreadExecutor()
        executor.execute {
            doThing
        }
    }

}