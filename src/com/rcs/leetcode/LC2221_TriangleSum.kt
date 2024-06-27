package com.rcs.leetcode

fun main() {
    println(LC2221_TriangleSum.triangularSum(intArrayOf(1,2,3,4,5)))
}

class LC2221_TriangleSum {

    companion object {

        /**
         * https://leetcode.com/problems/find-triangular-sum-of-an-array/description/
         *
         * Input: nums = [1,2,3,4,5]
         * Output: 8
         */

        fun triangularSum(nums: IntArray): Int {
            var queue = ArrayDeque<Int>()
            nums.forEach { queue.add(it) }
            while (queue.size > 1) {
                val nextQueue = ArrayDeque<Int>()
                var current = queue.removeFirst()
                while (queue.isNotEmpty()) {
                    val next = queue.removeFirst()
                    nextQueue.add((current + next) % 10)
                    current = next
                }
                queue = nextQueue
            }
            return queue.removeFirst()
        }
    }
}