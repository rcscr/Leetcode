package com.rcs.leetcode

fun main() {
    println(LC0242_Anagram.isAnagram("anagram", "nagaram"))
    println(LC0242_Anagram.isAnagram("rat", "car"))
    println(LC0242_Anagram.isAnagram("Clint Eastwood", "old west action"))
}

class LC0242_Anagram {

    companion object {

        /**
         * https://leetcode.com/problems/valid-anagram/
         *
         * Example 1:
         * Input: s = "anagram", t = "nagaram"
         * Output: true
         *
         * Example 2:
         * Input: s = "rat", t = "car"
         * Output: false
         */

        fun isAnagram(a: String, b: String): Boolean {
            val histogram = mutableMapOf<Char, Int>()

            a.normalize().forEach {
                histogram[it] = 1 + histogram.getOrDefault(it, 0)
            }

            b.normalize().forEach {
                when (val occurrences = histogram[it]) {
                    null -> return false
                    else -> {
                        when (val updatedOccurrences = occurrences - 1) {
                            0 -> histogram.remove(it)
                            else -> histogram[it] = updatedOccurrences
                        }
                    }
                }
            }

            return histogram.isEmpty()
        }

        private fun String.normalize(): String {
            return this.lowercase().replace(" ", "")
        }
    }
}