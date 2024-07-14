package com.rcs.leetcode

fun main() {
    println(LC2544_AlternateDigitSum.alternateDigitSum(521))
    println(LC2544_AlternateDigitSum.alternateDigitSum(111))
    println(LC2544_AlternateDigitSum.alternateDigitSum(886996))
}

class LC2544_AlternateDigitSum {

    companion object {

        /**
         * https://leetcode.com/problems/alternating-digit-sum/
         *
         * Example 1:
         * Input: n = 521
         * Output: 4
         * Explanation: (+5) + (-2) + (+1) = 4.
         *
         * Example 2:
         * Input: n = 111
         * Output: 1
         * Explanation: (+1) + (-1) + (+1) = 1.
         */

        data class FoldState(val sum: Int, val nextSign: Int)

        fun alternateDigitSum(n: Int): Int {
            return n.toString()
                .fold(FoldState(0, +1)) { state, nextInt ->
                    FoldState(
                        state.sum + nextInt.digitToInt() * state.nextSign,
                        if (state.nextSign == 1) -1 else 1
                    )
                }.sum
        }
    }
}