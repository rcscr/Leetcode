package com.rcs.leetcode

import java.util.*
import kotlin.Comparator
import kotlin.NoSuchElementException
import kotlin.collections.ArrayDeque
import kotlin.math.max

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

/**
 * From:
 * https://github.com/raphael-correa-ng/Symmetree
 */
class BalancedBinarySearchTree<K, V> where K: Comparable<K> {

    private var root: BstNode<K, V>? = null

    val height: Int get() = root?.height() ?: 0

    fun contains(key: K): Boolean {
        return get(key) != null
    }

    fun get(key: K): V? {
        return findRecursively(key, root)?.value
    }

    fun reOrderIterator(): Iterator<BstEntry<K, V>> {
        return ReOrderBstIterator(root)
    }

    /**
     * Returns the previous value, if any, associated with this key
     */
    @Synchronized
    fun add(key: K, value: V): V? {
        if (root == null) {
            root = BstNode(key, value, null, null, null)
            return null
        } else {
            val newRootAndPreviousValue = insert(key, value, root!!)
            root = newRootAndPreviousValue.root
            return newRootAndPreviousValue.previousValue
        }
    }

    /**
     * Returns the previous value, if any, associated with this key
     */
    @Synchronized
    fun remove(key: K): V? {
        return remove(key, root)?.let {
            root = it.root
            it.previousValue
        }
    }

    private fun insert(key: K, value: V, node: BstNode<K, V>): NewRootAndPreviousValue<K, V> {
        val newInsertAndPreviousValue = insertRecursively(key, value, node)
        return NewRootAndPreviousValue(
            rebalance(newInsertAndPreviousValue.inserted),
            newInsertAndPreviousValue.previousValue
        )
    }

    private fun insertRecursively(key: K, value: V, node: BstNode<K, V>): NewInsertAndPreviousValue<K, V> {
        return when (key.compareTo(node.key)) {
            -1 -> when (node.left) {
                null -> {
                    node.left = BstNode(key, value, null, null, node)
                    NewInsertAndPreviousValue(node.left!!, null)
                }
                else ->
                    insertRecursively(key, value, node.left!!)
            }
            0 -> {
                val previousValue = node.value
                node.value = value
                NewInsertAndPreviousValue(node, previousValue)
            }
            1 -> when (node.right) {
                null -> {
                    node.right = BstNode(key, value, null, null, node)
                    NewInsertAndPreviousValue(node.right!!, null)
                }
                else ->
                    insertRecursively(key, value, node.right!!)
            }
            else -> throw AssertionError()
        }
    }

    internal fun findRecursively(key: K, node: BstNode<K, V>?): BstNode<K, V>? {
        return node?.let {
            when (key.compareTo(it.key)) {
                -1 -> findRecursively(key, it.left)
                0 -> it
                1 -> findRecursively(key, it.right)
                else -> throw AssertionError()
            }
        }
    }

    private fun remove(key: K, node: BstNode<K, V>?): NewRootAndPreviousValue<K, V>? {
        return node?.let {
            when (key.compareTo(it.key)) {
                -1 -> remove(key, it.left)
                0 -> NewRootAndPreviousValue(
                    unlink(it)?.let { unliked -> rebalance(unliked) },
                    it.value
                )
                1 -> remove(key, it.right)
                else -> throw AssertionError()
            }
        }
    }

    private fun unlink(node: BstNode<K, V>): BstNode<K, V>? {
        return when {
            // Case 1: Node has a left child
            node.left != null -> {
                val newRoot = predecessor(node)!!
                replace(node, newRoot)
                newRoot
            }
            // Case 2: Node has a right child
            node.right != null -> {
                val newRoot = successor(node)!!
                replace(node, newRoot)
                newRoot
            }
            // Case 3: Node is not root and has no children (a leaf node)
            !node.isRoot() -> {
                when (node) {
                    node.parent!!.left -> node.parent!!.left = null
                    node.parent!!.right -> node.parent!!.right = null
                }
                node.parent
            }
            // Case 4: Node is root and has no children
            else -> null
        }
    }

    private fun rebalance(node: BstNode<K, V>): BstNode<K, V> {
        val leftHeight = node.left?.height() ?: 0
        val rightHeight = node.right?.height() ?: 0

        val balance = leftHeight - rightHeight

        return when {
            balance < -1 -> {
                node.right?.let {
                    if ((it.left?.height() ?: 0) > (it.right?.height() ?: 0)) {
                        node.right = rotateRight(node.right!!)
                    }
                }
                rebalance(rotateLeft(node))
            }
            balance > 1 -> {
                node.left?.let {
                    if ((it.right?.height() ?: 0) > (it.left?.height() ?: 0)) {
                        node.left = rotateLeft(node.left!!)
                    }
                }
                rebalance(rotateRight(node))
            }
            node.parent != null ->
                rebalance(node.parent!!)
            else ->
                node
        }
    }

    private fun rotateRight(node: BstNode<K, V>): BstNode<K, V> {
        // new root
        val leftChild = node.left!!

        node.left = leftChild.right
        if (leftChild.right != null) {
            leftChild.right!!.parent = node
        }

        leftChild.right = node
        leftChild.parent = node.parent

        if (node.parent != null) {
            if (node.parent!!.left == node) {
                node.parent!!.left = leftChild
            } else {
                node.parent!!.right = leftChild
            }
        }

        node.parent = leftChild

        return leftChild
    }

    private fun rotateLeft(node: BstNode<K, V>): BstNode<K, V> {
        // new root
        val rightChild = node.right!!

        node.right = rightChild.left
        if (rightChild.left != null) {
            rightChild.left!!.parent = node
        }

        rightChild.left = node
        rightChild.parent = node.parent

        if (node.parent != null) {
            if (node.parent!!.left == node) {
                node.parent!!.left = rightChild
            } else {
                node.parent!!.right = rightChild
            }
        }

        node.parent = rightChild

        return rightChild
    }

    fun <K, V> predecessor(node: BstNode<K, V>): BstNode<K, V>? {
        return rightMost(node.left)
    }

    fun <K, V> successor(node: BstNode<K, V>): BstNode<K, V>? {
        return leftMost(node.right)
    }

    fun <K: Comparable<K>, V> findStart(startInclusive: K, node: BstNode<K, V>?): BstNode<K, V>? {
        return node?.let {
            when (startInclusive.compareTo(it.key)) {
                -1 -> {
                    if (it.left == null || (it.left!!.key < startInclusive && it.left!!.right == null)) {
                        it
                    } else {
                        findStart(startInclusive, it.left)
                    }
                }
                0 -> it
                1 -> {
                    if (it.right == null || (it.right!!.key < startInclusive && it.right!!.left == null)) {
                        it
                    } else {
                        findStart(startInclusive, it.right)
                    }
                }
                else -> throw AssertionError()
            }
        }
    }

    fun <K, V> replace(node: BstNode<K, V>, replacement: BstNode<K, V>) {
        node.key = replacement.key
        node.value = replacement.value
        if (replacement.isLeaf()) {
            when (replacement) {
                replacement.parent?.left -> replacement.parent!!.left = null
                replacement.parent?.right -> replacement.parent!!.right = null
            }
        } else {
            replace(replacement, (predecessor(replacement) ?: successor(replacement))!!)
        }
    }

    private data class NewRootAndPreviousValue<K, V>(
        val root: BstNode<K, V>?,
        val previousValue: V?
    )

    private data class NewInsertAndPreviousValue<K, V>(
        val inserted: BstNode<K, V>,
        val previousValue: V?
    )

    class ReOrderBstIterator<K, V>(root: BstNode<K, V>?): Iterator<BstEntry<K, V>> {

        internal var next = rightMost(root)

        override fun hasNext(): Boolean {
            return next != null
        }

        override fun next(): BstEntry<K, V> {
            val toReturn = next!!
            setNext()
            return BstEntry(toReturn.key, toReturn.value)
        }

        private fun setNext() {
            if (next?.left != null) {
                next = rightMost(next?.left)
            } else {
                while (next?.parent != null && next == next?.parent?.left) {
                    next = next?.parent
                }
                next = next?.parent
            }
        }
    }
}

data class BstNode<K, V>(
    var key: K,
    var value: V,
    var left: BstNode<K, V>?,
    var right: BstNode<K, V>?,
    var parent: BstNode<K, V>?
) {

    fun isRoot(): Boolean {
        return parent == null
    }

    fun isLeaf(): Boolean {
        return left == null && right == null
    }

    fun height(): Int {
        return 1 + max(left?.height() ?: 0, right?.height() ?: 0)
    }
}

class BstEntry<K, V>(val key: K, val value: V)

private fun <K, V> leftMost(node: BstNode<K, V>?): BstNode<K, V>? {
    var leftMost = node
    while (leftMost?.left != null) {
        leftMost = leftMost.left!!
    }
    return leftMost
}

private fun <K, V> rightMost(node: BstNode<K, V>?): BstNode<K, V>? {
    var rightMost = node
    while (rightMost?.right != null) {
        rightMost = rightMost.right!!
    }
    return rightMost
}