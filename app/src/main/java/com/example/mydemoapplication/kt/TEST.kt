package com.example.mydemoapplication.kt

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Package: com.example.mydemoapplication.kt
 * Date:    2023/4/24
 * Desc:    com.example.mydemoapplication.kt
 *
 * @author liujicheng
 */
object TEST {

    fun main() {
        test()
        hello {
            println("noinline Say Hello!")
            it.length
        }
    }

    fun test() {
        hello {
            println("noinline Say Hello!")
            it.length
        }

    }

    //使用 noinline 标记 block
      fun hello(   block: (str: String) -> Unit) {
        println("Say Hello!")
        block.invoke("invoke")

        println("hello end")

    }

    fun testLet() {
        val str = "li"
        str.let {

        }
        println(str)


        val list = mutableListOf<String>()
    }

}