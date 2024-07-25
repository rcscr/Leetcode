package com.rcs.leetcode

fun main() {
    println(LC0036_ValidSudoku.isValidSudoku(
        arrayOf(
            charArrayOf('5','3','.','.','7','.','.','.','.'),
            charArrayOf('6','.','.','1','9','5','.','.','.'),
            charArrayOf('.','9','8','.','.','.','.','6','.'),
            charArrayOf('8','.','.','.','6','.','.','.','3'),
            charArrayOf('4','.','.','8','.','3','.','.','1'),
            charArrayOf('7','.','.','.','2','.','.','.','6'),
            charArrayOf('.','6','.','.','.','.','2','8','.'),
            charArrayOf('.','.','.','4','1','9','.','.','5'),
            charArrayOf('.','.','.','.','8','.','.','7','9')
        )
    )) // true 

    println(LC0036_ValidSudoku.isValidSudoku(
        arrayOf(
            charArrayOf('8','3','.','.','7','.','.','.','.'),
            charArrayOf('6','.','.','1','9','5','.','.','.'),
            charArrayOf('.','9','8','.','.','.','.','6','.'),
            charArrayOf('8','.','.','.','6','.','.','.','3'),
            charArrayOf('4','.','.','8','.','3','.','.','1'),
            charArrayOf('7','.','.','.','2','.','.','.','6'),
            charArrayOf('.','6','.','.','.','.','2','8','.'),
            charArrayOf('.','.','.','4','1','9','.','.','5'),
            charArrayOf('.','.','.','.','8','.','.','7','9')
        )
    )) // false
}

class LC0036_ValidSudoku {

    companion object {

        /**
         * https://leetcode.com/problems/valid-sudoku/
         */

        fun isValidSudoku(board: Array<CharArray>): Boolean {
            val bitset = BooleanArray(10) { false }

            // for each row
            for (row in board) {
                for (char in row) {
                    if (!validateAndSet(char, bitset)) {
                        return false
                    }
                }
                bitset.fill(false)
            }

            // for each column
            for (i in board.indices) {
                for (j in board[i].indices) {
                    val char = board[j][i]
                    if (!validateAndSet(char, bitset)) {
                        return false
                    }
                }
                bitset.fill(false)
            }

            return true
        }

        private fun validateAndSet(char: Char, bitset: BooleanArray): Boolean {
            if (!isValidSudokuCell(char)) {
                return false
            }
            if (!char.isEmptyCell()) {
                if (bitset[char.digitToInt()]) {
                    return false
                }
                bitset[char.digitToInt()] = true
            }
            return true
        }

        private fun Char.isEmptyCell(): Boolean {
            return this == '.'
        }

        private fun isValidSudokuCell(char: Char): Boolean {
            return char.isEmptyCell() || (char.digitToInt() in 1..9)
        }
    }
}
