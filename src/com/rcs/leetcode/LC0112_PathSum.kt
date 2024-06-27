package com.rcs.leetcode

fun main() {
    // true
    println(LC112_PathSum.hasPathSum(arrayOf(5,4,8,11,null,13,4,7,2,null,null,null,null,null,1), 22))
    // true
    println(LC112_PathSum.hasPathSum(arrayOf(5,4,8,11,null,13,4,7,2,null,null,null,null,null,1), 27))
    // true
    println(LC112_PathSum.hasPathSum(arrayOf(5,4,8,11,null,13,4,7,2,null,null,null,null,null,1), 26))
    // true
    println(LC112_PathSum.hasPathSum(arrayOf(5,4,8,11,null,13,4,7,2,null,null,null,null,null,1), 18))
    // false
    println(LC112_PathSum.hasPathSum(arrayOf(5,4,8,11,null,13,4,7,2,null,null,null,null,null,1), 30))
}

class LC112_PathSum {

    companion object {

        /**
         * https://leetcode.com/problems/path-sum/description/
         *
         * (The original description on LeetCode is missing two "null" entries - it's been corrected here)
         *
         * Input: root = [5,4,8,11,null,13,4,7,2,null,null,null,null,null,1], targetSum = 22
         * Output: true
         * Explanation: The root-to-leaf path with the target sum is shown.
         */

        fun hasPathSum(tree: Array<Int?>, targetSum: Int): Boolean {
            val leafNodes = tree.indices
                .filter { index ->
                    val leftChildIndex = leftOf(index)
                    val rightChildIndex = rightOf(index)
                    tree[index] != null
                            && (leftChildIndex !in tree.indices || tree[leftChildIndex] == null)
                            && (rightChildIndex !in tree.indices || tree[rightChildIndex] == null)
                }

            return leafNodes.any {
                var index = it
                var sum = 0
                do {
                    sum += tree[index]!!
                    index = parentOf(index)
                } while (index >= 0)
                sum == targetSum
            }
        }
    }
}