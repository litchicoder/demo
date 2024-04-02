package com.example.mydemoapplication.kt

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Package: com.example.mydemoapplication.kt
 * Date:    2023/4/24
 * Desc:    编译器不知道高阶函数中初始化变量/编译器不知道函数返回不为空
 *
 * @author liujicheng
 */
@OptIn(ExperimentalContracts::class)
object TEST_Contract {

    fun main() {
        test()
    }

    inline fun test() {
        val sref: String
        runFun {
            sref = "gdfg"
        }
//        sref.ifEmpty {  }
//        sref.let {  }
//        sref.also {  }
//        sref.takeIf { true }
//        println(sref)


        val str: String? = null
//        if (testisNotNull(str)) {
////        if (str.isNotEmpty()) {
//            str.get(0)
//        }
    }

    @ExperimentalContracts
    inline fun runFun( action: () -> Unit) {
        //执行契约
        contract {
            callsInPlace(action, InvocationKind.EXACTLY_ONCE)
        }
        testisNotNull(action)
        Thread().start()
//        testisNotNull("")
    }

//    @ExperimentalContracts
    inline fun testisNotNull(action: () -> Unit): Boolean {
//        contract {
//            returns(true) implies (str != null)
//        }
        return true
    }

}