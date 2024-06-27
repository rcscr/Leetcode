package com.rcs.leetcode

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

        // indices are inclusive
        data class WaterContainer(val indices: Pair<Int, Int>, val area: Int)

        fun maxArea(heights: IntArray): WaterContainer {
            return heights
                .foldIndexed(WaterContainer(Pair(0, 0), 0)) { i, maxWaterContainer, _ ->
                    maxAreaFrom(i, heights).let {
                        when(it.area > maxWaterContainer.area) {
                            true -> it
                            else -> maxWaterContainer
                        }
                    }
                }
        }

        private fun maxAreaFrom(i: Int, heights: IntArray): WaterContainer {
            return ((i + 1)..<heights.size)
                .fold(WaterContainer(Pair(0, 0), 0)) { maxWaterContainer, j ->
                    val candidateMaxContainer = min(heights[i], heights[j]) * (j - i)
                    when (candidateMaxContainer > maxWaterContainer.area) {
                        true -> WaterContainer(Pair(i, j), candidateMaxContainer)
                        else -> maxWaterContainer
                    }
                }
        }
    }
}