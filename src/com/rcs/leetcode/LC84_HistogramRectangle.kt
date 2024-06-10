package com.rcs.leetcode

import kotlin.math.max

fun main() {
    // A rectangle whose height is the start: [5, 6] = [5, 5] = 10
    println(LC84_HistogramRectangle.largestRectangleArea(intArrayOf(2, 1, 5, 6, 2, 3)))

    // A rectangle whose height is less than the start: [5, 6, 4] = [4, 4, 4] = 12
    println(LC84_HistogramRectangle.largestRectangleArea(intArrayOf(2, 1, 5, 6, 4, 3)))
}

class LC84_HistogramRectangle {

    companion object {

        /**
         * https://leetcode.com/problems/largest-rectangle-in-histogram/description/
         *
         * Input: heights = [2,1,5,6,2,3]
         * Output: 10
         */

        fun largestRectangleArea(heights: IntArray): Int {
            var maxArea = 0
            for (i in heights.indices) {
                val maxAreaHeightConstant = maxAreaHeightConstant(i, heights)
                val maxAreaHeightVariable = maxAreaHeightVariable(i, heights)
                maxArea = max(maxArea, max(maxAreaHeightConstant, maxAreaHeightVariable))
            }
            return maxArea
        }

        private fun maxAreaHeightVariable(i: Int, heights: IntArray): Int {
            val nextMinHeight = (i..<heights.size).minOf { heights[it] }
            val heightsRemaining = heights.size - i
            return nextMinHeight * heightsRemaining
        }

        private fun maxAreaHeightConstant(i: Int, heights: IntArray): Int {
            var maxArea = heights[i]
            for (j in (i+1)..<heights.size) {
                if (heights[j] < heights[i]) {
                    break
                } else {
                    maxArea += heights[i]
                }
            }
            return maxArea
        }
    }
}