package com.rcs.leetcode

fun main() {
    println(LC0020_ValidParanthesis.isValid("()"))
    println(LC0020_ValidParanthesis.isValid("()[]{}"))
    println(LC0020_ValidParanthesis.isValid("(]"))
}

class LC0020_ValidParanthesis {

    companion object {

        /**
         * https://leetcode.com/problems/valid-parentheses/description/
         *
         * Example 1:
         * Input: s = "()"
         * Output: true
         *
         * Example 2:
         * Input: s = "()[]{}"
         * Output: true
         *
         * Example 3:
         * Input: s = "(]"
         * Output: false
         */

        private val closeToOpenParenthesis = mapOf(')' to '(', '}' to '{', ']' to '[')

        fun isValid(s: String): Boolean {
            val stack = ArrayDeque<Char>()

            for (char in s.toCharArray()) {
                if (closeToOpenParenthesis.containsKey(char)) {
                    if (stack.isEmpty() || stack.removeLast() != closeToOpenParenthesis[char]) {
                        return false
                    }
                } else {
                    stack.add(char)
                }
            }

            return stack.isEmpty()
        }
    }
}