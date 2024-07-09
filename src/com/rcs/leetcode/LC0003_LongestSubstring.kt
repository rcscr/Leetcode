package com.rcs.leetcode

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

        data class LongestSubstring(val indices: Pair<Int, Int>, val substring: String)

        fun lengthOfLongestSubstring(string: String): LongestSubstring {
            return string.indices
                .fold(LongestSubstring(Pair(0, 0), "")) { longest, i ->
                    var j = i + 1
                    while (j + 1 < string.length && !contains(string, i, j, string[j])) {
                        j++
                    }
                    val candidateLongest = j - i
                    when (candidateLongest > longest.indices.second - longest.indices.first) {
                        true -> LongestSubstring(Pair(i, j), string.substring(i, j))
                        else -> longest
                    }
                }
        }

        fun contains(string: String, fromInclusive: Int, toExclusive: Int, character: Char): Boolean {
            return (fromInclusive..<toExclusive)
                .any { string[it] == character }
        }
    }
}