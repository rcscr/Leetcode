package com.rcs.leetcode

fun main() {
    println(LC0056_MergeIntervals.merge(
        arrayOf(intArrayOf(1, 3), intArrayOf(2, 6), intArrayOf(8, 10), intArrayOf(15,18))
    ).map { it.contentToString() })
    println(LC0056_MergeIntervals.merge(
        arrayOf(intArrayOf(1, 4), intArrayOf(4, 5))
    ).map { it.contentToString() })
}

class LC0056_MergeIntervals {

    companion object {

        /**
         * https://leetcode.com/problems/merge-intervals/description/
         *
         * Example 1:
         * Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
         * Output: [[1,6],[8,10],[15,18]]
         *
         * Example 2:
         * Input: intervals = [[1,4],[4,5]]
         * Output: [[1,5]]
         */

        fun merge(intervals: Array<IntArray>): Array<IntArray> {
            val merged = Array<IntArray?>(intervals.size) { null }

            var j = 0
            merged[j] = intervals[j]

            for (i in 1..<intervals.size) {
                val current = intervals[i]
                val previous = intervals[j]

                if (previous[1] >= current[0]) {
                    merged[j] = intArrayOf(previous[0], current[1])
                } else {
                    merged[++j] = current
                }
            }

            return merged
                .takeWhile { it != null }
                .map { it!! }
                .toTypedArray()
        }
    }
}