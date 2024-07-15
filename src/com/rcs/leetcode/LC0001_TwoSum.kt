package com.rcs.leetcode

fun main() {
    println(LC0001_TwoSum.twoSum(intArrayOf(2,7,11,15), 9).contentToString())
    println(LC0001_TwoSum.twoSum(intArrayOf(3,2,4), 6).contentToString())
    println(LC0001_TwoSum.twoSum(intArrayOf(3,3), 6).contentToString())
}

class LC0001_TwoSum {

    companion object {

        /**
         * https://leetcode.com/problems/two-sum/
         *
         * Example 1:
         * Input: nums = [2,7,11,15], target = 9
         * Output: [0,1]
         *
         * Example 2:
         * Input: nums = [3,2,4], target = 6
         * Output: [1,2]
         *
         * Example 3:
         * Input: nums = [3,3], target = 6
         * Output: [0,1]
         */

        fun twoSum(nums: IntArray, target: Int): IntArray? {
            for (i in 0..<nums.size - 1) {
                for (j in i + 1..<nums.size) {
                    if (nums[i] + nums[j] == target) {
                        return intArrayOf(i, j)
                    }
                }
            }
            return null
        }
    }
}