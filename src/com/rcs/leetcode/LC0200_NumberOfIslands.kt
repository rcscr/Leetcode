package com.rcs.leetcode

fun main() {
    val grid = arrayOf(
        charArrayOf('1', '1', '0', '0', '0'),
        charArrayOf('1', '1', '0', '0', '0'),
        charArrayOf('0', '0', '1', '0', '0'),
        charArrayOf('0', '0', '0', '1', '1'),
    )
    println(LC200_NumberOfIslands.numIslands(grid))
}

class LC200_NumberOfIslands {

    companion object {

        /**
         * https://leetcode.com/problems/number-of-islands/description/
         *
         * Example 1:
         * Input: grid = [
         *   ["1","1","1","1","0"],
         *   ["1","1","0","1","0"],
         *   ["1","1","0","0","0"],
         *   ["0","0","0","0","0"]
         * ]
         * Output: 1
         *
         * Example 2:
         * Input: grid = [
         *   ["1","1","0","0","0"],
         *   ["1","1","0","0","0"],
         *   ["0","0","1","0","0"],
         *   ["0","0","0","1","1"]
         * ]
         * Output: 3
         */

        fun numIslands(grid: Array<CharArray>): Int {
            val visited = grid.map { it.map { false }.toTypedArray() }.toTypedArray()

            var numIslands = 0

            for (i in grid.indices) {
                for (j in grid[i].indices) {
                    val islandSize = getIslandSize(i, j, grid, visited)
                    if (islandSize > 0) {
                        numIslands++
                    }
                }
            }

            return numIslands
        }

        private fun getIslandSize(i: Int, j: Int, grid: Array<CharArray>, visited: Array<Array<Boolean>>): Int {
            if (i < 0 || j < 0 || i >= grid.size || j >= grid[0].size) {
                return 0
            }

            if (visited[i][j] || grid[i][j] == '0') {
                return 0
            }

            visited[i][j] = true

            var islandSize = 1

            islandSize += getIslandSize(i-1, j, grid, visited)
            islandSize += getIslandSize(i+1, j, grid, visited)
            islandSize += getIslandSize(i, j-1, grid, visited)
            islandSize += getIslandSize(i, j+1, grid, visited)

            return islandSize
        }
    }
}