package com.rcs.leetcode

fun main() {
    println(LC0021_MergeSortedLists.merge(intArrayOf(1, 2, 4), intArrayOf(1, 3, 4))
        .contentToString())
}

class LC0021_MergeSortedLists {

    companion object {

        /**
         * https://leetcode.com/problems/merge-two-sorted-lists/description/
         *
         * Input: list1 = [1,2,4], list2 = [1,3,4]
         * Output: [1,1,2,3,4,4]
         */

        fun merge(list1: IntArray, list2: IntArray): IntArray {
            val merged = IntArray(list1.size + list2.size)

            var i = 0
            var j = 0

            while (i < list1.size || j < list2.size) {
                merged[j + i] = if (j == list2.size) {
                    list1[i++]
                } else if (i == list1.size) {
                    list2[j++]
                } else if (list1[i] <= list2[j]) {
                    list1[i++]
                } else {
                    list2[j++]
                }
            }

            return merged
        }
    }
}