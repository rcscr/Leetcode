package com.rcs.leetcode

fun main() {
    println(LC0205_IsomorphicStrings.areIsomorphic("egg", "add"))
    println(LC0205_IsomorphicStrings.areIsomorphic("foo", "bar"))
    println(LC0205_IsomorphicStrings.areIsomorphic("paper", "title"))
}

class LC0205_IsomorphicStrings {

    companion object {

        /**
         * https://leetcode.com/problems/isomorphic-strings/
         *
         * Example 1:
         * Input: s = "egg", t = "add"
         * Output: true
         *
         * Example 2:
         * Input: s = "foo", t = "bar"
         * Output: false
         *
         * Example 3:
         * Input: s = "paper", t = "title"
         * Output: true
         */

        fun areIsomorphic(s: String, t: String): Boolean {
            if (s.length != t.length) {
                return false
            }

            val mappings = mutableMapOf<Char, Char>()

            for (i in s.indices) {
                val thisChar = s[i]
                val thatChar = t[i]

                if (mappings.contains(thisChar)) {
                    if (mappings[thisChar] != thatChar) {
                        return false
                    }
                } else {
                    mappings[thisChar] = thatChar
                }
            }

            return true
        }
    }
}