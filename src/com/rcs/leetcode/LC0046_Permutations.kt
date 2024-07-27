package com.rcs.leetcode

fun main() {
    println(LC0046_Permutations.permute(arrayOf(1, 2, 3)))
    println(LC0046_Permutations.permute(arrayOf(0, 1)))
    println(LC0046_Permutations.permute(arrayOf(1)))
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


        fun <T> permute(nums: Array<T>): List<List<T>> {
            return permute(nums, 0, nums.size - 1)
        }

        private fun <T> permute(nums: Array<T>, start: Int, end: Int): List<List<T>> {
            return when (end - start) {
                0 -> listOf(listOf(nums[start]))
                1 -> listOf(listOf(nums[start], nums[end]), listOf(nums[end], nums[start]))
                else -> combine(nums[start], permute(nums, start + 1, end))
            }
        }

        private fun <T> combine(number: T, permutations: List<List<T>>): List<List<T>> {
            return permutations.flatMap { list ->
                (0..list.size).map { i ->
                    val newList = mutableListOf<T>()
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