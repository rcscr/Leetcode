package com.rcs.leetcode

fun main() {
    println(LC0572_SubtreeOfAnotherTree
        .isSubtree(arrayOf(3,4,5,1,2), arrayOf(4,1,2)))
    println(LC0572_SubtreeOfAnotherTree
        .isSubtree(arrayOf(3,4,5,1,2,null,null,null,null,0), arrayOf(4,1,2)))
}

class LC0572_SubtreeOfAnotherTree {

    companion object {

        /**
         * https://leetcode.com/problems/subtree-of-another-tree/
         *
         * Input: root = [3,4,5,1,2], subRoot = [4,1,2]
         * Output: true
         *
         * Input: root = [3,4,5,1,2,null,null,null,null,0], subRoot = [4,1,2]
         * Output: false
         */

        fun isSubtree(root: Array<Int?>, subRoot: Array<Int?>): Boolean {
            return isSubtree(0, root, 0, subRoot)
        }

        private fun isSubtree(
            rootIndex: Int,
            root: Array<Int?>,
            subRootIndex: Int,
            subRoot: Array<Int?>
        ): Boolean {
            val rootNull = rootIndex >= root.size || root[rootIndex] == null
            val subRootNull = subRootIndex >= subRoot.size || subRoot[subRootIndex] == null

            if (rootNull xor subRootNull) {
                return false
            } else if (rootNull && subRootNull) {
                return true
            }

            if (root[rootIndex] == subRoot[subRootIndex]) {
                return isSubtree(leftOf(rootIndex), root, leftOf(subRootIndex), subRoot)
                        && isSubtree(rightOf(rootIndex), root, rightOf(subRootIndex), subRoot)
            }

            return isSubtree(leftOf(rootIndex), root, 0, subRoot)
                    || isSubtree(rightOf(rootIndex), root, 0, subRoot)
        }
    }
}