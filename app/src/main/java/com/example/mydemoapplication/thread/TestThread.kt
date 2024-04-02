package com.example.mydemoapplication.thread

class TestThread() {
    @Synchronized
    fun runTest(threadName: String) {
        for (counter in 0..4) {
            println("$threadName counter is$counter")
            try {
                Thread.sleep(100)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    fun runTest1(threadName: String) {
        for (counter in 0..4) {
            println("$threadName counter is$counter")
            try {
                Thread.sleep(100)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}