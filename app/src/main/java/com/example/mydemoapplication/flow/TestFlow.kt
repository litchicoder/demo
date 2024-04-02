package com.example.mydemoapplication.flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking


/**
 * Package: com.example.mydemoapplication.flow
 * Date:    2023/6/8
 * Desc:    com.example.mydemoapplication.flow
 *
 * @author liujicheng
 */

object TestFlow {
    fun flowTest(callback:()->Unit) {
        runBlocking {
            val flow = flow {
                println("collect:flow ${Thread.currentThread()}")
                kotlinx.coroutines.delay(5_000)
                this.emit("hello word flow")
            }

            flow
                .flowOn(Dispatchers.IO)
                .collect {
                println("collect:$it ${Thread.currentThread()}")
                    callback.invoke()
            }
        }

    }
}

