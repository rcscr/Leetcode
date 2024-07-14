package com.rcs.leetcode

fun main() {
    println(LC0283_MoveZeroes.moveZeroes(intArrayOf(0,1,0,3,12))
        .contentToString())
}

class LC0283_MoveZeroes {

    companion object {

        /**
         * https://leetcode.com/problems/move-zeroes/
         *
         * Input: nums = [0,1,0,3,12]
         * Output: [1,3,12,0,0]
         */

        fun moveZeroes(nums: IntArray): IntArray{
            for (i in nums.indices) {
                if (nums[i] == 0) {
                    val index = firstNonZero(nums, i+1) ?: break
                    nums[i] = nums[index]
                    nums[index] = 0
                }
            }
            return nums
        }

        fun firstNonZero(nums: IntArray, startIndex: Int): Int? {
            for (i in startIndex..<nums.size) {
                if (nums[i] != 0) {
                    return i
                }
            }
            return null
        }
    }
}