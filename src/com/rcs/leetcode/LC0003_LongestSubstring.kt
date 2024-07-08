package com.rcs.leetcode

import kotlin.math.max

fun main() {
    println(LC0003_LongestSubstring.lengthOfLongestSubstring("abcabcbb"))
    println(LC0003_LongestSubstring.lengthOfLongestSubstring("bbbbb"))
    println(LC0003_LongestSubstring.lengthOfLongestSubstring("pwwkew"))
}

class LC0003_LongestSubstring {

    companion object {

        /**
         * https://leetcode.com/problems/longest-substring-without-repeating-characters/description/
         *
         * Example 1:
         *
         * Input: s = "abcabcbb"
         * Output: 3
         * Explanation: The answer is "abc", with the length of 3.
         * Example 2:
         *
         * Input: s = "bbbbb"
         * Output: 1
         * Explanation: The answer is "b", with the length of 1.
         * Example 3:
         *
         * Input: s = "pwwkew"
         * Output: 3
         * Explanation: The answer is "wke", with the length of 3.
         */

        fun lengthOfLongestSubstring(string: String): Int {
            return string.indices
                .fold(0) { longest, i ->
                    var j = i + 1
                    while (j + 1 < string.length && !contains(string, i, j, string[j])) {
                        j++
                    }
                    val candidateLongest = j - i
                    max(longest, candidateLongest)
                }
        }

        fun contains(string: String, fromInclusive: Int, toExclusive: Int, character: Char): Boolean {
            return (fromInclusive..<toExclusive)
                .any { string[it] == character }
        }
    }
}