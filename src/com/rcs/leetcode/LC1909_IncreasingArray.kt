package com.rcs.leetcode

fun main() {
    println(LC1909_IncreasingArray.canBeIncreasing(intArrayOf(1,2,10,5,7)))
    println(LC1909_IncreasingArray.canBeIncreasing(intArrayOf(1,2,10,1,11)))
    println(LC1909_IncreasingArray.canBeIncreasing(intArrayOf(2,3,1,2)))
    println(LC1909_IncreasingArray.canBeIncreasing(intArrayOf(1,1,1)))
}

class LC1909_IncreasingArray {

    companion object {

        /**
         * https://leetcode.com/problems/remove-one-element-to-make-the-array-strictly-increasing/
         *
         * Example 1:
         * Input: nums = [1,2,10,5,7]
         * Output: true
         *
         * Example 2:
         * Input: nums = [2,3,1,2]
         * Output: false
         *
         * Example 3:
         * Input: nums = [1,1,1]
         * Output: false
         */

        fun canBeIncreasing(nums: IntArray): Boolean {
            var removalCount = 0

            var last = nums[0]

            for (i in 1..<nums.size) {
                if (nums[i] <= last) {
                    removalCount++
                    if (i - 2 < 0 || nums[i] > nums[i - 2]) {
                        last = nums[i]
                    }
                } else {
                    last = nums[i]
                }
            }

            return removalCount <= 1
        }
    }
}