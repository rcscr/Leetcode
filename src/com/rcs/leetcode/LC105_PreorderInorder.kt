package com.rcs.leetcode

fun main() {
    // original: [3, 9, 20, null, null, 15, 7]
    println(LC105_PreorderInorder.buildTree(intArrayOf(3, 9, 20, 15, 7), intArrayOf(9, 3, 15, 20, 7)).contentToString())

    // left-skewed tree: [1, 2, null, 3, null, null, null, 4]
    println(LC105_PreorderInorder.buildTree(intArrayOf(1, 2, 3, 4), intArrayOf(4, 3, 2, 1)).contentToString())

    // right-skewed tree: [1, null, 2, null, null, null, 3, null, null, null, null, null, null, null, 4]
    println(LC105_PreorderInorder.buildTree(intArrayOf(1, 2, 3, 4), intArrayOf(1, 2, 3, 4)).contentToString())
}

class LC105_PreorderInorder {

    companion object {

        /**
         * https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/description/
         *
         * Input: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
         * Output: [3,9,20,null,null,15,7]
         */

        fun buildTree(preorder: IntArray, inorder: IntArray): Array<Int?> {
            val size = preorder.size

            assert(size == inorder.size)

            val result = Array<Int?>(maxNodes(size)) { null }

            val preorderDeque = ArrayDeque<Int>(size)
            preorder.forEach { preorderDeque.add(it) }

            result[0] = buildTree(
                0,
                result,
                preorderDeque,
                inorder,
                preorderDeque.removeFirst(),
                0,
                size
            )

            return trim(result)
        }

        private fun buildTree(
            rootIndex: Int,
            result: Array<Int?>,
            preorder: ArrayDeque<Int>,
            inorder: IntArray,
            root: Int,
            start: Int,
            end: Int,
        ): Int? {

            if (end - start == 1) {
                return inorder[start]
            }

            if (preorder.isEmpty()) {
                return null
            }

            val inorderIndexOfRoot = inorder.indexOf(root)

            // left
            preorder
                .firstOrNull { inorder.indexOf(it) < inorderIndexOfRoot }
                ?.let {
                    preorder.remove(it)
                    val leftIndex = leftOf(rootIndex)
                    result[leftIndex] =
                        buildTree(
                            leftIndex,
                            result,
                            preorder,
                            inorder,
                            it,
                            start,
                            inorderIndexOfRoot
                        )
                }

            // right
            preorder
                .firstOrNull { inorder.indexOf(it) > inorderIndexOfRoot }
                ?.let {
                    preorder.remove(it)
                    val rightIndex = rightOf(rootIndex)
                    result[rightIndex] =
                        buildTree(
                            rightIndex,
                            result,
                            preorder,
                            inorder,
                            it,
                            inorderIndexOfRoot + 1,
                            end
                        )
                }

            return root
        }

        private fun leftOf(index: Int): Int {
            return 2 * index + 1
        }

        private fun rightOf(index: Int): Int {
            return 2 * index + 2
        }

        private fun maxNodes(height: Int): Int {
            // Using the formula 2^(N+1) - 1
            return (1 shl (height + 1)) - 1
        }

        private fun trim(result: Array<Int?>): Array<Int?> {
            val indexOfLastNonNull = result.indices.reversed().first { result[it] != null }
            return result.copyOfRange(0, indexOfLastNonNull + 1)
        }
    }
}