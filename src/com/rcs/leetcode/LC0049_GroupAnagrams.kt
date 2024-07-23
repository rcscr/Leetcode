package com.rcs.leetcode

fun main() {
    println(LC0049_GroupAnagrams.groupAnagrams(
        arrayOf("eat","tea","tan","ate","nat","bat")))
}

class LC0049_GroupAnagrams {

    companion object {

        /**
         * https://leetcode.com/problems/group-anagrams/
         *
         * Example 1:
         * Input: strs = ["eat","tea","tan","ate","nat","bat"]
         * Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
         */

        fun groupAnagrams(strs: Array<String>): List<List<String>> {
            val set = mutableSetOf<String>()
            set.addAll(strs)

            val groups = mutableListOf<List<String>>()

            while (set.isNotEmpty()) {
                val str = set.first()
                val group = mutableListOf<String>()
                group.add(str)
                set.remove(str)
                for (otherStr in set) {
                    if (areAnagrams(str, otherStr)) {
                        group.add(otherStr)
                    }
                }
                set.removeAll(group)
                groups.add(group)
            }

            return groups
        }

        fun areAnagrams(a: String, b: String): Boolean {
            if (a.length != b.length) {
                return false
            }

            val histogram = mutableMapOf<Char, Int>()

            for (c in a) {
                histogram[c] = 1 + (histogram[c] ?: 0)
            }

            for (c in b) {
                when (val occurrences = histogram[c]) {
                    null -> return false
                    else -> {
                        when (val updatedOccurrences = occurrences - 1) {
                            0 -> histogram.remove(c)
                            else -> histogram[c] = updatedOccurrences
                        }
                    }
                }
            }

            return histogram.isEmpty()
        }
    }
}