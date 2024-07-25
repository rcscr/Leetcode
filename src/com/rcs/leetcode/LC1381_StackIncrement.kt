package com.rcs.leetcode

import java.util.ArrayList
import kotlin.math.min

fun main() {
    val customStack = LC1381_StackIncrement.Companion.CustomStack(10)

    customStack.push(1)
    customStack.push(2)
    customStack.push(3)

    println(customStack.pop()) // 3

    customStack.increment(2, 5)

    println(customStack.pop()) // 7
    println(customStack.pop()) // 6
}

class LC1381_StackIncrement {

    companion object {

        /**
         * https://leetcode.com/problems/design-a-stack-with-increment-operation/
         */

        class CustomStack(maxSize: Int) {

            private val list = ArrayList<Int>(maxSize)

            fun push(x: Int) {
                list.add(x)
            }

            fun pop(): Int? {
                return if (list.isNotEmpty()) {
                    list.removeLast()
                } else {
                    null
                }
            }

            fun increment(k: Int, value: Int) {
                for (i in 0..<(min(list.size, k))) {
                    list[i] += value
                }
            }
        }
    }
}