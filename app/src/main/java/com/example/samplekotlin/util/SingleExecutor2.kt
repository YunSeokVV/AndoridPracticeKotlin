package com.example.samplekotlin.util

import java.util.concurrent.Executors

class SingleExecute : ExecutorInterface {
    private val executor = Executors.newSingleThreadExecutor()
    override fun executeApi(task: () -> Unit) {
        executor.execute {
            task.invoke()
        }
    }
}