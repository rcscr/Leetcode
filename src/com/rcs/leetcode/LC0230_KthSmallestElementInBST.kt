package com.rcs.leetcode

fun main() {
    println(LC0230_KthSmallestElementInBST.kthSmallest(arrayOf(3,1,4,null,2), 1))
    println(LC0230_KthSmallestElementInBST.kthSmallest(arrayOf(5,3,6,2,4,null,null,1), 3))
}

class LC0230_KthSmallestElementInBST {

    companion object {

        /**
         * https://leetcode.com/problems/kth-smallest-element-in-a-bst/description/
         *
         * Input: root = [3,1,4,null,2], k = 1
         * Output: 1
         *
         * Input: root = [5,3,6,2,4,null,null,1], k = 3
         * Output: 3
         */

        fun kthSmallest(tree: Array<Int?>, k: Int): Int {
            val bst = BalancedBinarySearchTree<Int, Unit>()
            tree.filterNotNull().forEach { bst.add(it, Unit) }
            var j = 0
            for (element in bst.inOrderIterator()) {
                if (++j == k) {
                    return element.key
                }
            }
            throw AssertionError()
        }
    }
}