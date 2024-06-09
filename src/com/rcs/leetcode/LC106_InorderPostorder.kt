package com.rcs.leetcode

fun main() {
    // original: [3, 9, 20, null, null, 15, 7]
    println(LC106_InorderPostorder.buildTree(intArrayOf(9, 3, 15, 20, 7), intArrayOf(9, 15, 7, 20, 3)).contentToString())

    // left-skewed tree: [1, 2, null, 3, null, null, null, 4]
    println(LC106_InorderPostorder.buildTree(intArrayOf(4, 3, 2, 1), intArrayOf(4, 3, 2, 1)).contentToString())

    // right-skewed tree: [1, null, 2, null, null, null, 3, null, null, null, null, null, null, null, 4]
    println(LC106_InorderPostorder.buildTree(intArrayOf(1, 2, 3, 4), intArrayOf(4, 3, 2, 1)).contentToString())
}

class LC106_InorderPostorder {

    companion object {

        /**
         * https://leetcode.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/description/
         *
         * Input: inorder = [9,3,15,20,7], postorder = [9,15,7,20,3]
         * Output: [3,9,20,null,null,15,7]
         */

        fun buildTree(inorder: IntArray, postorder: IntArray): Array<Int?> {
            val size = inorder.size

            assert(size == postorder.size)

            val result = Array<Int?>(maxNodes(size)) { null }

            val postorderDeque = ArrayDeque<Int>(size)
            postorder.forEach { postorderDeque.add(it) }

            result[0] = buildTree(
                0,
                result,
                inorder,
                postorderDeque,
                postorderDeque.removeLast(),
                0,
                size
            )

            return trim(result)
        }

        private fun buildTree(
            rootIndex: Int,
            result: Array<Int?>,
            inorder: IntArray,
            postorder: ArrayDeque<Int>,
            root: Int,
            start: Int,
            end: Int,
        ): Int? {

            if (end - start == 1) {
                return inorder[start]
            }

            val inorderIndexOfRoot = inorder.indexOf(root)

            // left
            postorder
                .reversed()
                .firstOrNull { inorder.indexOf(it) < inorderIndexOfRoot }
                ?.let {
                    postorder.remove(it)
                    val leftIndex = leftOf(rootIndex)
                    result[leftIndex] = buildTree(
                        leftIndex,
                        result,
                        inorder,
                        postorder,
                        it,
                        start,
                        inorderIndexOfRoot
                    )
                }

            // right
            postorder
                .lastOrNull { inorder.indexOf(it) > inorderIndexOfRoot }
                ?.let {
                    postorder.remove(it)
                    val rightIndex = rightOf(rootIndex)
                    result[rightIndex] = buildTree(
                        rightIndex,
                        result,
                        inorder,
                        postorder,
                        it,
                        inorderIndexOfRoot + 1,
                        end
                    )
                }

            return when {
                postorder.isEmpty() -> root
                else -> null
            }
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