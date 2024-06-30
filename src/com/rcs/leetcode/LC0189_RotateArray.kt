package com.rcs.leetcode

import java.util.*
import kotlin.math.abs

fun main() {
    // [5, 6, 7, 1, 2, 3, 4]
    println(Arrays.toString(LC0189_RotateArray.rotate(intArrayOf(1, 2, 3, 4, 5, 6, 7), 3)))

    // [4, 5, 6, 7, 1, 2, 3]
    println(Arrays.toString(LC0189_RotateArray.rotate(intArrayOf(1, 2, 3, 4, 5, 6, 7), -3)))
}

class LC0189_RotateArray {

    companion object {

        /**
         * https://leetcode.com/problems/rotate-array/
         *
         * Input: nums = [1,2,3,4,5,6,7], k = 3
         * Output: [5,6,7,1,2,3,4]
         */

        // rotates in place using O(1) memory
        fun rotate(nums: IntArray, k: Int): IntArray {
            val direction = when {
                k > 0 ->  1
                k < 0 -> -1
                else -> throw IllegalArgumentException("Parameter `k` cannot be 0.")
            }
            for (i in 0..<abs(k)) {
                rotateOnce(nums, direction)
            }
            return nums
        }

        private fun rotateOnce(nums: IntArray, direction: Int) {
            var current = nums[0]
            for (j in nums.indices) {
                val index = when (direction) {
                     1 -> (j + 1) % nums.size
                    -1 -> nums.size - 1 - j
                    else -> throw AssertionError()
                }
                val temp = nums[index]
                nums[index] = current
                current = temp
            }
        }
    }
}