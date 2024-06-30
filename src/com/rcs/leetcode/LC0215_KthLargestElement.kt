package com.rcs.leetcode

fun main() {
    println(LC0215_KthLargestElement.findKthLargest(intArrayOf(3,2,1,5,6,4), 2))
    println(LC0215_KthLargestElement.findKthLargest(intArrayOf(3,2,3,1,2,4,5,5,6), 4))
}

class LC0215_KthLargestElement {

    companion object {

        /**
         * https://leetcode.com/problems/kth-largest-element-in-an-array/description/
         *
         * Example 1:
         * Input: nums = [3,2,1,5,6,4], k = 2
         * Output: 5
         *
         * Example 2:
         * Input: nums = [3,2,3,1,2,4,5,5,6], k = 4
         * Output: 4
         *
         * Can you solve it without sorting?
         * - yes
         */

        fun findKthLargest(nums: IntArray, k: Int): Int {
            val bst = BalancedBinarySearchTree<Int, Int>()
            nums.forEach {
                bst.add(it, 1 + (bst.get(it) ?: 0))
            }
            var j = 0
            for (entry in bst.reOrderIterator()) {
                j += entry.value // i.e. occurrences
                if (j >= k) {
                    return entry.key
                }
            }
            throw AssertionError()
        }
    }
}