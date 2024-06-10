package com.rcs.leetcode

import kotlin.math.max
import kotlin.math.min

fun main() {
    println(LC011_WaterContainer.maxArea(intArrayOf(1, 8, 6, 2, 5, 4, 8, 3, 7)))
}

class LC011_WaterContainer {

    companion object {

        /**
         * https://leetcode.com/problems/container-with-most-water/description/
         *
         * Input: height = [1,8,6,2,5,4,8,3,7]
         * Output: 49
         */

        fun maxArea(heights: IntArray): Int {
            return heights
                .foldIndexed(0) { i, maxArea, _ ->
                    max(maxArea, maxAreaFrom(i, heights))
                }
        }

        private fun maxAreaFrom(i: Int, heights: IntArray): Int {
            return ((i + 1)..<heights.size)
                .fold(0) { maxArea, j ->
                    val candidateMaxArea = min(heights[i], heights[j]) * (j - i)
                    max(maxArea, candidateMaxArea)
                }
        }
    }
}