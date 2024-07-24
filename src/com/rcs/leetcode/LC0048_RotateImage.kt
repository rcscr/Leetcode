package com.rcs.leetcode

fun main() {
    println(LC0048_RotateImage.rotate90Clockwise(
        arrayOf(
            intArrayOf(1,2,3),
            intArrayOf(4,5,6),
            intArrayOf(7,8,9)
        ))
        .contentDeepToString())

    println(LC0048_RotateImage.rotate90Clockwise(
        arrayOf(
            intArrayOf(5,1,9,11),
            intArrayOf(2,4,8,10),
            intArrayOf(13,3,6,7),
            intArrayOf(15,14,12,16)
        ))
        .contentDeepToString())
}

class LC0048_RotateImage {

    companion object {

        /**
         * https://leetcode.com/problems/rotate-image/description/
         *
         * Example 1:
         * Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
         * Output: [[7,4,1],[8,5,2],[9,6,3]]
         *
         * Example 2:
         * Input: matrix = [[5,1,9,11],[2,4,8,10],[13,3,6,7],[15,14,12,16]]
         * Output: [[15,13,2,5],[14,3,4,1],[12,6,8,9],[16,7,10,11]]
         */

        fun rotate90Clockwise(matrix: Array<IntArray>): Array<IntArray> {
            val N = matrix.size

            val layers = N / 2

            for (i in 0..layers) {

                val rotations = N - 1 - (2 * i)

                for (j in 1..rotations) {
                    rotateClockwise(matrix, i)
                }
            }

            return matrix
        }

        private fun rotateClockwise(matrix: Array<IntArray>, layer: Int) {
            val N = matrix.size - 2 * layer

            if (N == 1) {
                return
            }

            var next = matrix[layer][layer]

            // TOP
            for (i in layer + 1..<N + layer) {
                val temp = matrix[layer][i]
                matrix[layer][i] = next
                next = temp
            }

            // RIGHT
            for (j in layer + 1..<N + layer) {
                val temp = matrix[j][N + layer - 1]
                matrix[j][N + layer - 1] = next
                next = temp
            }

            // BOTTOM
            for (k in (layer..N + layer - 2).reversed()) {
                val temp = matrix[N + layer - 1][k]
                matrix[N + layer - 1][k] = next
                next = temp
            }

            // LEFT
            for (l in (layer..N + layer - 2).reversed()) {
                val temp = matrix[l][layer]
                matrix[l][layer] = next
                next = temp
            }
        }
    }
}
