package com.example.samplekotlin.util

import com.example.samplekotlin.database.PlantDatabase
import java.util.concurrent.Executor
import java.util.concurrent.Executors

// 처음 executor를 추상화 해보기 위해서 만들었던 인터페이스입니다.
//뷰 단에서 이 인터페이스를 실행시키고 매개변수만 넘겨줘서 할 일을 넘겨주면 DB에 데이터를 저장하거나 레트로핏을 사용해서 통신을 하는게 가능할 줄 알았는데 안됐습니다.
interface ExecutorInterface {

    // 비동기 작업을 이 인터페이스에서 할 수 있어야 한다. 이 디폴트 함수는 익명 객체를 만들어서 동작시키기 위해서 만들었다.
    fun executeAsync(task: () -> Unit) {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            task.invoke()
        }
    }

    // 인터페이스의 추상메소드를 사용했다. executor 가 필요한 시점에 뷰단에서 익명객체를 만들어서 사용한다.
    fun executerAsync(task: () -> Unit)

}