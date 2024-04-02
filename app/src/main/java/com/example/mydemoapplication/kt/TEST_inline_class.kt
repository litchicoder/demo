package com.example.mydemoapplication.kt

/**
 * Package: com.example.mydemoapplication.kt
 * Date:    2023/5/6
 * Desc:    com.example.mydemoapplication.kt
 *
 * @author liujicheng
 */
class TEST_inline_class {

    inline class ColorInline(val value: String) {

        fun getColor(): String {
            return value + "123"
        }
    }

    fun printColor(color: ColorInline) {
        println(color.getColor())
    }

    fun main() {
        val color = ColorInline("red")
        printColor(color)
    }


}