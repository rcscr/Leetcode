package com.rcs.leetcode

fun main() {
    println(LC1877_MinimizedPairSum.minPairSum(intArrayOf(3,5,2,3)))
    println(LC1877_MinimizedPairSum.minPairSum(intArrayOf(3,5,4,2,4,6)))
}

class LC1877_MinimizedPairSum {

    companion object {

        /**
         * https://leetcode.com/problems/minimize-maximum-pair-sum-in-array/description/
         *
         * Example 1:
         * Input: nums = [3,5,2,3]
         * Output: 7
         * Explanation: The elements can be paired up into pairs (3,3) and (5,2).
         * The maximum pair sum is max(3+3, 5+2) = max(6, 7) = 7.
         *
         * Example 2:
         * Input: nums = [3,5,4,2,4,6]
         * Output: 8
         * Explanation: The elements can be paired up into pairs (3,5), (4,4), and (6,2).
         * The maximum pair sum is max(3+5, 4+4, 6+2) = max(8, 8, 8) = 8.
         */

        fun minPairSum(nums: IntArray): Int {
            val bst = BalancedBinarySearchTree<Int, Int>()
            nums.forEach { bst.add(it, 1 + (bst.get(it) ?: 0)) }

            val pairs = Array<Pair<Int, Int>?>(nums.size / 2) {
                val min = bst.getMin()!!
                val max = bst.getMax()!!

                if (min.value == 1) bst.remove(min.key)
                else bst.add(min.key, min.value - 1)

                if (max.value == 1) bst.remove(max.key)
                else bst.add(max.key, max.value - 1)

                Pair(min.key, max.key)
            }

            return pairs.maxOf { it!!.first + it.second }
        }
    }
}