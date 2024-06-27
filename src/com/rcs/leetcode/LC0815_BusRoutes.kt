package com.rcs.leetcode

fun main() {
    println(LC815_BusRoutes.numBusesToDestination(arrayOf(intArrayOf(1,2,7), intArrayOf(3,6,7)), 1, 6))
}

class LC815_BusRoutes {

    companion object {

        /**
         * https://leetcode.com/problems/bus-routes/description/
         *
         * Input: routes = [[1,2,7],[3,6,7]], source = 1, target = 6
         * Output: 2
         */

        fun numBusesToDestination(routes: Array<IntArray>, source: Int, target: Int): Int {
            val graph = UnidirectionalPropertyGraph<Int, MutableSet<Int>>()

            for (routeIndex in routes.indices) {
                val route = routes[routeIndex]

                // add stops (nodes)
                for (stop in route) {
                    if (!graph.contains(stop)) {
                        graph.addNode(stop, mutableSetOf())
                    }
                }

                // add edges
                route.fold(route[route.size-1]) { a, b ->
                    graph.addEdge(a, b)
                    b
                }

                // add routes that service each stop
                for (stop in route) {
                    val routesAtThisStop = graph.getValue(stop)!!
                    routesAtThisStop.add(routeIndex)
                    graph.setValue(stop, routesAtThisStop)
                }
            }

            val lightestPathByNumberOfRoutes = graph.getLightestPathSimple(
                source,
                target,
                { weight, stopA, stopB ->
                    graph.getValue(stopA)!!.forEach { weight.add(it) }
                    graph.getValue(stopB)!!.forEach { weight.add(it) }
                    weight
                },
                { weightA, weightB -> weightA.size.compareTo(weightB.size) },
                mutableSetOf<Int>()
            )

            return lightestPathByNumberOfRoutes?.weight?.size ?: -1
        }
    }
}