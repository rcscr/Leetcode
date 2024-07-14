package com.rcs.leetcode

fun main() {
    println(LC0023_MergeKSortedLists.mergeKLists(
        arrayOf(intArrayOf(1, 4, 5), intArrayOf(1, 3, 4), intArrayOf(2, 6))
    ).contentToString())
}

class LC0023_MergeKSortedLists {

    companion object {

        /**
         * https://leetcode.com/problems/merge-k-sorted-lists/
         *
         * Input: lists = [[1,4,5],[1,3,4],[2,6]]
         * Output: [1,1,2,3,4,4,5,6]
         */

        data class NextEntry(val listIndex: Int, val number: Int)

        fun mergeKLists(lists: Array<IntArray>): IntArray {
            val merged = IntArray(lists.sumOf { it.size })

            val indices = IntArray(lists.size) { 0 }

            var nextEntry = nextEntry(indices, lists)

            while (nextEntry != null) {
                merged[indices.sum()] = nextEntry.number
                indices[nextEntry.listIndex]++
                nextEntry = nextEntry(indices, lists)
            }

            return merged
        }

        private fun nextEntry(indices: IntArray, lists: Array<IntArray>): NextEntry? {
            return lists.foldIndexed<IntArray, NextEntry?>(null) { index, min, list ->
                if (indices[index] < list.size && (min == null || list[indices[index]] < min.number)) {
                    NextEntry(index, list[indices[index]])
                } else {
                    min
                }
            }
        }
    }
}