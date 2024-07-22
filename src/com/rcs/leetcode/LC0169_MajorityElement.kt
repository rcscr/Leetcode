package com.rcs.leetcode

fun main() {
    println(LC0169_MajorityElement.majorityElement(intArrayOf(3,2,3)))
    println(LC0169_MajorityElement.majorityElement(intArrayOf(2,2,1,1,1,2,2)))
}

class LC0169_MajorityElement {

    companion object {

        /**
         * https://leetcode.com/problems/majority-element/description/
         *
         * Example 1:
         *
         * Input: nums = [3,2,3]
         * Output: 3
         * Example 2:
         *
         * Input: nums = [2,2,1,1,1,2,2]
         * Output: 2
         *
         */

        //Boyer-Moore algorithm
        fun majorityElement(nums: IntArray): Int? {
            var element: Int? = null
            var votes = 0

            for (num in nums) {
                if (votes == 0) {
                    element = num
                }
                votes += when(num) {
                    element -> 1
                    else -> 0
                }
            }

            val count = nums.count { it == element }

            if (count > nums.size / 2) {
                return element
            }
            return null
        }
    }
}