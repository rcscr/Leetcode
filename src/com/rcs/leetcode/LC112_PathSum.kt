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

            // this works, but it's better solved with a BinaryTree;
            // will refactor later when I've written the tree

            val graph = UnidirectionalPropertyGraph<Int, Int>()

            tree.forEachIndexed { index, weight ->
                if (weight != null) {
                    graph.addNode(index, weight)
                }
            }

            tree.indices.forEach { index ->
                if (graph.contains(index)) {
                    if (graph.contains(leftOf(index))) {
                        graph.addEdge(index, leftOf(index))
                    }
                    if (graph.contains(rightOf(index))) {
                        graph.addEdge(index, rightOf(index))
                    }
                }
            }

            val leafNodes = tree.indices
                .filter { index ->
                    val leftChildIndex = leftOf(index)
                    val rightChildIndex = rightOf(index)
                    tree[index] != null
                            && (leftChildIndex !in tree.indices || tree[leftChildIndex] == null)
                            && (rightChildIndex !in tree.indices || tree[rightChildIndex] == null)
                }

            return leafNodes.any {
                graph.getShortestPathWithWeight(
                    0,
                    it,
                    { w, _, k -> w + graph.getValue(k)!! },
                    graph.getValue(0)!!
                )?.weight == targetSum
            }
        }
    }
}