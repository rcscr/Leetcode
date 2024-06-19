package com.rcs.leetcode

fun main() {
    println(LC124_TreeMaxSum.maxPathSum(arrayOf(-10, 9, 20, null, null, 15, 7)))
}

class LC124_TreeMaxSum {

    companion object {

        /**
         * https://leetcode.com/problems/binary-tree-maximum-path-sum/description/
         *
         * Input: root = [-10,9,20,null,null,15,7]
         * Output: 42
         * Explanation: The optimal path is 15 -> 20 -> 7 with a path sum of 15 + 20 + 7 = 42.
         */

        fun maxPathSum(tree: Array<Int?>): Int {
            val graph = UnidirectionalPropertyGraph<Int, Int>()

            // add nodes
            tree.forEachIndexed { index, weight ->
                if (weight != null) {
                    graph.addNode(index, weight)
                }
            }

            // add connections
            tree.indices.forEach { index ->
                if (graph.contains(index)) {
                    if (graph.contains(leftOf(index))) {
                        graph.addEdge(index, leftOf(index))
                        graph.addEdge(leftOf(index), index)
                    }
                    if (graph.contains(rightOf(index))) {
                        graph.addEdge(index, rightOf(index))
                        graph.addEdge(rightOf(index), index)
                    }
                }
            }

            return tree.indices
                .filter { graph.contains(it) }
                .maxOfOrNull { node ->
                    graph.getTransientConnections(node)
                        .maxOfOrNull { conn ->
                            // using negative numbers to compute the "lightest" path
                            // then convert to positive
                            -graph.getLightestPathSimple(
                                node,
                                conn,
                                { w, _, k -> w - graph.getValue(k)!! },
                                { w1, w2 -> Integer.compare(w1, w2) },
                                -graph.getValue(node)!!
                            )!!.weight
                        } ?: -1
                } ?: -1
        }
    }
}