package com.rcs.leetcode

import kotlin.math.min

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

        // indices are inclusive
        data class LargestRectangle(val indices: Pair<Int, Int>, val area: Int)

        fun largestRectangleArea(heights: IntArray): LargestRectangle {
            return heights
                .foldIndexed(LargestRectangle(Pair(0, 0), 0)) { i, largestRect, _ ->
                    largestRectangleStartingAt(i, heights).let {
                        when (it.area > largestRect.area) {
                            true -> it
                            else -> largestRect
                        }
                    }
                }
        }

        private fun largestRectangleStartingAt(i: Int, heights: IntArray): LargestRectangle {
            return (i + 1..<heights.size)
                .fold(LargestRectangle(Pair(i, i), heights[i])) { maxArea, index ->
                    val minHeight = min(heights[i], (i..index).minOf { heights[it] })
                    val heightsRemaining = index - i + 1
                    val candidateMaxArea = minHeight * heightsRemaining
                    when (candidateMaxArea > maxArea.area) {
                        true -> LargestRectangle(Pair(i, index), candidateMaxArea)
                        else -> maxArea
                    }
                }
        }
    }
}