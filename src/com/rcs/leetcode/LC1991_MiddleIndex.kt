package com.rcs.leetcode

fun main() {
    println(LC1991_MiddleIndex.findMiddleIndex(intArrayOf(2,3,-1,8,4)))
    println(LC1991_MiddleIndex.findMiddleIndex(intArrayOf(1,-1,4)))
    println(LC1991_MiddleIndex.findMiddleIndex(intArrayOf(2,5)))
}

class LC1991_MiddleIndex {

    companion object {

        /**
         * https://leetcode.com/problems/find-the-middle-index-in-array/
         *
         * Example 1:
         * Input: nums = [2,3,-1,8,4]
         * Output: 3
         *
         * Example 2:
         * Input: nums = [1,-1,4]
         * Output: 2
         *
         * Example 3:
         * Input: nums = [2,5]
         * Output: -1
         */

        fun findMiddleIndex(nums: IntArray): Int {
            val totalSum = nums.sum()

            var sumBefore = 0
            var sumAfter = totalSum

            for (i in nums.indices) {
                sumAfter -= nums[i]
                if (sumBefore == sumAfter) {
                    return i
                }
                sumBefore += nums[i]
            }

            return -1
        }
    }
}