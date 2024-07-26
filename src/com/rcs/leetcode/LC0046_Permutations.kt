package com.rcs.leetcode

fun main() {
    println(LC0046_Permutations.permute(intArrayOf(1, 2, 3)))
    println(LC0046_Permutations.permute(intArrayOf(0, 1)))
    println(LC0046_Permutations.permute(intArrayOf(1)))
}

class LC0046_Permutations {

    companion object {

        /**
         * https://leetcode.com/problems/permutations/
         *
         * Example 1:
         * Input: nums = [1,2,3]
         * Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
         *
         * Example 2:
         * Input: nums = [0,1]
         * Output: [[0,1],[1,0]]
         *
         * Example 3:
         * Input: nums = [1]
         * Output: [[1]]
         */

        fun permute(nums: IntArray): List<List<Int>> {
            return permute(nums, 0, nums.size - 1)
        }

        private fun permute(nums: IntArray, start: Int, end: Int): List<List<Int>> {
            return when (end - start) {
                0 -> listOf(listOf(nums[start]))
                1 -> listOf(listOf(nums[start], nums[end]), listOf(nums[end], nums[start]))
                else -> combine(nums[start], permute(nums, start + 1, end))
            }
        }

        private fun combine(number: Int, permutations: List<List<Int>>): List<List<Int>> {
            return permutations.flatMap { list ->
                (0..list.size).map { i ->
                    val newList = mutableListOf<Int>()
                    for (j in 0..<i) {
                        newList.add(list[j])
                    }
                    newList.add(number)
                    for (k in i..<list.size) {
                        newList.add(list[k])
                    }
                    newList
                }
            }
        }
    }
}