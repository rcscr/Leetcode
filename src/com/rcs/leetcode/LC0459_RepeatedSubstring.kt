package com.rcs.leetcode

fun main() {
    println(LC0459_RepeatedSubstring.repeatedSubstringPattern("abab"))
    println(LC0459_RepeatedSubstring.repeatedSubstringPattern("aba"))
    println(LC0459_RepeatedSubstring.repeatedSubstringPattern("abcabcabcabc"))
}

class LC0459_RepeatedSubstring {

    companion object {

        /**
         * https://leetcode.com/problems/repeated-substring-pattern/description/
         *
         * Example 1:
         * Input: s = "abab"
         * Output: true
         *
         * Example 2:
         * Input: s = "aba"
         * Output: false
         *
         * Example 3:
         * Input: s = "abcabcabcabc"
         * Output: true
         */

        fun repeatedSubstringPattern(s: String): Boolean {
            for (i in 1..s.length / 2) {
                val substring = s.substring(0, i)
                var constructed = substring + substring
                while (s.startsWith(constructed)) {
                    if (constructed == s) {
                        return true
                    }
                    constructed += substring
                }
            }
            return false
        }
    }
}