package com.example.samplekotlin.util

import java.util.concurrent.Executors

class SingleExecutorLauncher : ExecutorInterface {
    private val executor = Executors.newSingleThreadExecutor()

    override fun executerAsync(task: () -> Unit) {
        executor.execute {
            task.invoke()
        }
    }
}