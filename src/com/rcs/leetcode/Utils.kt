package com.rcs.leetcode

import java.util.*
import kotlin.Comparator
import kotlin.NoSuchElementException
import kotlin.collections.ArrayDeque

/**
 * From:
 * https://github.com/raphael-correa-ng/Trane
 */
class UnidirectionalPropertyGraph<K, V> {

    private data class Node<K, V>(val key: K, var value: V, val connections: MutableSet<K>)

    private val nodes = hashMapOf<K, Node<K, V>>()

    fun getConnections(key: K): Set<K>? {
        return nodes[key]?.connections?.toSet()
    }

    fun getTransientConnections(key: K): Set<K> {
        return getTransientConnections(key, mutableSetOf())
    }

    fun getValue(key: K): V? {
        return nodes[key]?.value
    }

    fun setValue(key: K, value: V) {
        if (!nodes.contains(key)) {
            throw NoSuchElementException("Node $key does not exist")
        }
        nodes[key]!!.value = value
    }

    fun contains(key: K): Boolean {
        return nodes[key] != null
    }

    fun getNodes(): Set<K> {
        return nodes.keys
    }

    fun addNode(key: K, value: V) {
        addNode(key, value, mutableSetOf())
    }

    fun addNode(key: K, value: V, connections: Set<K>) {
        val nonExistent = connections.minus(nodes.keys)
        if (nonExistent.isNotEmpty()) {
            throw NoSuchElementException("Connections do not exist: $nonExistent")
        }
        nodes[key] = Node(key, value, connections.toMutableSet())
    }

    fun addEdge(keyA: K, keyB: K) {
        if (keyA == keyB) {
            throw IllegalArgumentException("Node cannot connect to itself")
        }
        val nodeA = nodes[keyA] ?: throw NoSuchElementException("Node $keyA does not exist")
        val nodeB = nodes[keyB] ?: throw NoSuchElementException("Node $keyB does not exist")
        nodeA.connections.add(nodeB.key)
    }

    fun removeNodeAndConnections(key: K): Boolean {
        if (nodes.remove(key) != null) {
            for (value in nodes.values) {
                value.connections.remove(key)
            }
            return true
        }
        return false
    }

    fun removeEdge(keyA: K, keyB: K) {
        nodes[keyA]?.connections?.remove(keyB)
    }

    fun getDegreeOfSeparation(start: K, end: K): Int {
        return (getShortestPath(start, end)?.size ?: 0) - 1
    }

    fun getAllPaths(start: K, end: K): List<SequencedSet<K>> {
        return getAllPaths(start, end, linkedSetOf())
    }

    fun getShortestPath(start: K, end: K): SequencedSet<K>? {
        return getShortestPathWithWeight(start, end, { _, _, _ -> }, Unit)?.path
    }

    fun <W> getShortestPathWithWeight(
        start: K,
        end: K,
        weightAccumulator: (W, K, K) -> W,
        initialWeight: W
    ): WeightedPath<K, W>? {

        val pathQueue = ArrayDeque<WeightedPath<K, W>>()
        pathQueue.add(WeightedPath(linkedSetOf(start), initialWeight))

        while (pathQueue.isNotEmpty()) {
            val (candidatePath, candidateWeight) = pathQueue.removeFirst()
            val candidateNode = nodes[candidatePath.last()]!!

            if (candidateNode.connections.contains(end)) {
                return WeightedPath(candidatePath.concat(end), weightAccumulator(candidateWeight, candidateNode.key, end))
            }

            candidateNode.connections
                .filter { !candidatePath.contains(it) }
                .map { WeightedPath(candidatePath.concat(it), weightAccumulator(candidateWeight, candidateNode.key, it)) }
                .forEach { pathQueue.add(it) }
        }

        return null
    }

    /**
     * A convenience method that wraps the result of the weightAccumulator function
     * into a list, making it compatible with the function below
     */
    fun <W> getLightestPathSimple(
        start: K,
        end: K,
        weightAccumulator: (W, K, K) -> W,
        weightComparator: Comparator<W>,
        initialWeight: W
    ): WeightedPath<K, W>? {
        return getLightestPathComplex(
            start,
            end,
            { weight, nodeA, nodeB -> listOf(weightAccumulator(weight, nodeA, nodeB)) },
            weightComparator,
            initialWeight)
    }

    /**
     * This is a modified Dijkstra's algorithm that:
     * - computes the weight between two nodes via lambdas passed in as params
     * - splits an edge into multiple edges based on different weights
     */
    fun <W> getLightestPathComplex(
        start: K,
        end: K,
        weightAccumulator: (W, K, K) -> Iterable<W>,
        weightComparator: Comparator<W>,
        initialWeight: W
    ): WeightedPath<K, W>? {

        val pathQueue = PriorityQueue<WeightedPathPriorityQueueEntry<K, W>>()
        pathQueue.add(WeightedPathPriorityQueueEntry(linkedSetOf(start), initialWeight, weightComparator))

        val distances = mutableMapOf<K, W>()
        distances[start] = initialWeight

        var minWeightedPath: WeightedPath<K, W>? = null

        while (pathQueue.isNotEmpty()) {
            val (candidatePath, candidateWeight) = pathQueue.remove()
            val candidateNode = nodes[candidatePath.last()]!!

            if (candidateNode.connections.contains(end)) {
                val candidateMinWeight = weightAccumulator(candidateWeight, candidateNode.key, end).minWith(weightComparator)
                if (minWeightedPath == null || weightComparator.compare(candidateMinWeight, minWeightedPath.weight) < 0) {
                    minWeightedPath = WeightedPath(candidatePath.concat(end), candidateMinWeight)
                }
            }

            candidateNode.connections
                .filter { next -> !candidatePath.contains(next) }
                .forEach { next ->
                    val weights = weightAccumulator(candidateWeight, candidatePath.last(), next)
                    weights.forEach { weight ->
                        //  Only paths that offer a shorter distance to a node than any previously
                        //  known paths are added to the queue for further exploration
                        val shouldExplorePath = distances[next] == null
                                || weightComparator.compare(weight, distances[next]!!) < 0

                        if (shouldExplorePath) {
                            distances[next] = weight
                            val entry = WeightedPathPriorityQueueEntry(candidatePath.concat(next), weight, weightComparator)
                            pathQueue.add(entry)
                        }
                    }
                }
        }

        return minWeightedPath
    }

    private fun getTransientConnections(key: K, visited: MutableSet<K>): Set<K> {
        if (visited.contains(key)) {
            return mutableSetOf()
        }
        visited.add(key)
        val directConnections = getConnections(key) ?: mutableSetOf()
        return directConnections + directConnections
            .flatMap { getTransientConnections(it, visited) }
            .filter { it != key }
            .toSet()
    }

    private fun getAllPaths(start: K, end: K, visited: SequencedSet<K>): List<SequencedSet<K>> {
        val paths = mutableListOf<SequencedSet<K>>()

        if (nodes[start]?.connections?.contains(end) == true) {
            paths.add(linkedSetOf(start, end).concat(visited))
        }

        val updatedVisited = linkedSetOf(end).concat(visited) // order matters!
        paths.addAll(nodes.values
            .asSequence()
            .filter { it.key != start && !updatedVisited.contains(it.key) && it.connections.contains(end) }
            .flatMap { getAllPaths(start, it.key, updatedVisited) }
            .filter { it.isNotEmpty() }
            .toList())

        return paths
    }

    /**
     * The following two functions serve only to cast
     * the result of the + operation to a SequencedSet<K>
     */
    private fun SequencedSet<K>.concat(element: K): SequencedSet<K> {
        return (this + element) as SequencedSet<K>
    }

    private fun SequencedSet<K>.concat(set: SequencedSet<K>): SequencedSet<K> {
        return (this + set) as SequencedSet<K>
    }
}

data class WeightedPath<K, W>(val path: SequencedSet<K>, val weight: W)

private data class WeightedPathPriorityQueueEntry<K, W>(
    val path: SequencedSet<K>,
    val weight: W,
    private val comparator: Comparator<W>
): Comparable<WeightedPathPriorityQueueEntry<K, W>> {

    override fun compareTo(other: WeightedPathPriorityQueueEntry<K, W>): Int {
        return comparator.compare(weight, other.weight)
    }
}

fun leftOf(index: Int): Int {
    return 2 * index + 1
}

fun rightOf(index: Int): Int {
    return 2 * index + 2
}

fun parentOf(index: Int): Int {
    return if (index == 0) -1 else (index - 1) / 2
}

fun maxNodes(height: Int): Int {
    // Using the formula 2^(N+1) - 1
    return (1 shl (height + 1)) - 1
}

fun trim(result: Array<Int?>): Array<Int?> {
    val indexOfLastNonNull = result.indices.reversed().first { result[it] != null }
    return result.copyOfRange(0, indexOfLastNonNull + 1)
}