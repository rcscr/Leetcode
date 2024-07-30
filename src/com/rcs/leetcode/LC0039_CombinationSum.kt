package com.rcs.leetcode

fun main() {
    println(LC0039_CombinationSum.combinationSum(intArrayOf(2,3,6,7), 7))
    println(LC0039_CombinationSum.combinationSum(intArrayOf(2,3,5), 8))
    println(LC0039_CombinationSum.combinationSum(intArrayOf(5,6,8), 19))
}

class LC0039_CombinationSum {

    companion object {

        /**
         * https://leetcode.com/problems/combination-sum/
         *
         * Example 1:
         *
         * Input: candidates = [2,3,6,7], target = 7
         * Output: [[2,2,3],[7]]
         * Explanation:
         * 2 and 3 are candidates, and 2 + 2 + 3 = 7. Note that 2 can be used multiple times.
         * 7 is a candidate, and 7 = 7.
         * These are the only two combinations.
         *
         * Example 2:
         * Input: candidates = [2,3,5], target = 8
         * Output: [[2,2,2,2],[2,3,3],[3,5]]
         */

        fun combinationSum(candidates: IntArray, target: Int): List<List<Int>> {
            val results = mutableSetOf<List<Int>>()

            for (i in candidates.indices) {
                if (candidates[i] == target) {
                    results.add(mutableListOf(candidates[i]))
                }

                for (j in candidates.indices) {
                    val sum = candidates[i] + candidates[j]

                    if (sum > target) {
                        break
                    } else {
                        val combination = mutableListOf(candidates[i], candidates[j])

                        if (sum == target) {
                            results.add(combination.sorted())
                        } else {
                            val newTarget = target - sum
                            combinationSum(candidates, newTarget)
                                .map { (it + combination).sorted() }
                                .forEach { results.add(it) }
                        }
                    }
                }
            }

            return results.toList()
        }
    }
}
