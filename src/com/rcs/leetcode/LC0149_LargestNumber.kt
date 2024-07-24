package com.rcs.leetcode

import java.util.Comparator

fun main() {
    println(LC0149_LargestNumber.largestNumber(intArrayOf(10, 2)))
    println(LC0149_LargestNumber.largestNumber(intArrayOf(3, 30, 34, 5, 9)))
}

class LC0149_LargestNumber {

    companion object {

        /**
         * https://leetcode.com/problems/largest-number/
         *
         * Example 1:
         * Input: nums = [10,2]
         * Output: "210"
         *
         * Example 2:
         * Input: nums = [3,30,34,5,9]
         * Output: "9534330"
         */

        fun largestNumber(nums: IntArray): String {
            val comparator =  Comparator<Int> { a, b ->
                (a.toString() + b).toInt().compareTo((b.toString() + a).toInt())
            }.reversed()

            return nums
                .sortedWith(comparator)
                .joinToString("")
        }
    }
}