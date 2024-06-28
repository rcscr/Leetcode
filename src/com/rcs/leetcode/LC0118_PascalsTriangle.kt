package com.rcs.leetcode

import java.util.*

fun main() {
    LC0118_PascalsTriangle.generate(5).forEach { println(Arrays.toString(it)) }
}

class LC0118_PascalsTriangle {

    companion object {

        /**
         * https://leetcode.com/problems/pascals-triangle/description/
         *
         * Input: numRows = 5
         * Output: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]
         */
        fun generate(numRows: Int): List<IntArray> {
            return (1..<numRows)
                .fold(mutableListOf(intArrayOf(1))) { triangle, _ ->
                    val previousRow = triangle.last()
                    val nextRow = IntArray(previousRow.size + 1) { 1 }
                    for (i in 0..<previousRow.size-1) {
                        nextRow[i + 1] = previousRow[i] + previousRow[i + 1]
                    }
                    triangle.add(nextRow)
                    triangle
                }

        }
    }
}