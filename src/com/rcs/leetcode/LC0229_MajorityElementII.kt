package com.rcs.leetcode

fun main() {
    println(LC0229_MajorityElementII.majorityElement(intArrayOf(3, 2, 3)))
    println(LC0229_MajorityElementII.majorityElement(intArrayOf(1)))
    println(LC0229_MajorityElementII.majorityElement(intArrayOf(1, 2)))
}

class LC0229_MajorityElementII {

    companion object {

        /**
         * https://leetcode.com/problems/majority-element-ii/
         *
         * Example 1:
         * Input: nums = [3,2,3]
         * Output: [3]
         *
         * Example 2:
         * Input: nums = [1]
         * Output: [1]
         *
         * Example 3:
         * Input: nums = [1,2]
         * Output: [1,2]
         */

        fun majorityElement(nums: IntArray): List<Int> {
            val histogram = mutableMapOf<Int, Int>()
            nums.forEach { histogram[it] = 1 + (histogram[it] ?: 0) }
            return histogram.entries
                .filter { it.value > nums.size / 3 }
                .map { it.key }
        }
    }
}