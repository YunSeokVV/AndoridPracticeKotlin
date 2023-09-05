package com.example.samplekotlin.util

import java.util.concurrent.Executors

class SingleExecutor {
    private val executor = Executors.newSingleThreadExecutor()
    fun executeTask(task: () -> Unit) {
        executor.execute {
            task.invoke()
        }
    }
}