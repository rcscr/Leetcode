package com.rcs.leetcode

import com.rcs.leetcode.LC0020_ValidParanthesis.Companion.isValid
import com.rcs.leetcode.LC0046_Permutations.Companion.permute

fun main() {
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
            return permute(("(".repeat(n - 1) + ")".repeat(n - 1)).toCharArray().toTypedArray())
                .distinct()
                .map { "(${it.joinToString("")})" }
                .filter { isValid(it) }
        }
    }
}