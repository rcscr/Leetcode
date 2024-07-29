package com.rcs.leetcode

import com.rcs.leetcode.LC0020_ValidParanthesis.Companion.isValid
import com.rcs.leetcode.LC0046_Permutations.Companion.permute

fun main() {
    println(LC0022_GenerateParantheses.generateParenthesis(1))
    println(LC0022_GenerateParantheses.generateParenthesis(2))
    println(LC0022_GenerateParantheses.generateParenthesis(3))
    println(LC0022_GenerateParantheses.generateParenthesis(4))
}

class LC0022_GenerateParantheses {

    companion object {

        /**
         * https://leetcode.com/problems/generate-parentheses/description/
         *
         * Example 1:
         * Input: n = 3
         * Output: ["((()))","(()())","()()()","(())()","()(())"]
         *
         * Example 2:
         * Input: n = 1
         * Output: ["()"]
         */

        fun generateParenthesis(n: Int): List<String> {
            if (n <= 0) {
                throw IllegalArgumentException()
            }

            val inner = when (n - 1) {
                0 -> " "
                else -> ("(".repeat(n - 1) + ")".repeat(n - 1))
            }.toCharArray().toTypedArray()

            return permute(inner)
                .distinct()
                .map { "(${it.joinToString("").trim()})" }
                .filter { isValid(it) }
        }
    }
}