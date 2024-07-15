package com.rcs.leetcode

fun main() {
    println(LC0042_TrappingRainWater.trap(intArrayOf(0,1,0,2,1,0,1,3,2,1,2,1)))
    println(LC0042_TrappingRainWater.trap(intArrayOf(4,2,0,3,2,5)))
}

class LC0042_TrappingRainWater {

    companion object {

        /**
         * https://leetcode.com/problems/trapping-rain-water/
         *
         * Example 1:
         * Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
         * Output: 6
         *
         * Example 2:
         * Input: height = [4,2,0,3,2,5]
         * Output: 9
         */

        fun trap(height: IntArray): Int {
            var rainWater = 0
            var i = 0
            while (i < height.size) {
                var j = i + 1
                var newRainWater = 0
                while (j < height.size && height[j] < height[i]) {
                    newRainWater += height[i] - height[j]
                    j++
                }
                if (j < height.size) {
                    rainWater += newRainWater
                    i = j
                } else {
                    i++
                }
            }
            return rainWater
        }
    }
}