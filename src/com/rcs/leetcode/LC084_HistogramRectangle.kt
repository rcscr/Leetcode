package com.rcs.leetcode

import kotlin.math.max

fun main() {
    // A rectangle whose height is the start: [5, 6] = [5, 5] = 10
    println(LC084_HistogramRectangle.largestRectangleArea(intArrayOf(2, 1, 5, 6, 2, 3)))

    // A rectangle whose height is less than the start: [5, 6, 4] = [4, 4, 4] = 12
    println(LC084_HistogramRectangle.largestRectangleArea(intArrayOf(2, 1, 5, 6, 4, 1)))
}

class LC084_HistogramRectangle {

    companion object {

        /**
         * https://leetcode.com/problems/largest-rectangle-in-histogram/description/
         *
         * Input: heights = [2,1,5,6,2,3]
         * Output: 10
         */

        fun largestRectangleArea(heights: IntArray): Int {
            return heights
                .foldIndexed(0) { i, maxArea, _ ->
                    val maxAreaHeightConstant = maxAreaHeightConstant(i, heights)
                    val maxAreaHeightVariable = maxAreaHeightVariable(i, heights)
                    max(maxArea, max(maxAreaHeightConstant, maxAreaHeightVariable))
                }
        }

        private fun maxAreaHeightVariable(i: Int, heights: IntArray): Int {
            return (i+1..<heights.size)
                .fold(heights[i]) { maxArea, index ->
                    val minHeight = (i..index).minOf { heights[it] }
                    val heightsRemaining = index - i + 1
                    val candidateMaxArea = minHeight * heightsRemaining
                    max(maxArea, candidateMaxArea)
                }
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