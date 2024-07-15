package com.rcs.leetcode

fun main() {
    val cache = LC0146_LRUCache.Companion.LRUCache(2)
    cache.put(1, 1)
    cache.put(2, 2)
    println(cache.get(1))
    cache.put(3, 3)
    println(cache.get(2))
    cache.put(4, 4)
    println(cache.get(1))
    println(cache.get(3))
    println(cache.get(4))
}

class LC0146_LRUCache {

    companion object {

        /**
         * NOTE: The example provided by Leetcode is wrong.
         * The solution I provide is correct.
         * It seems there was a confusion between "least" and "most" recently used key.
         * My solution evicts the "least" recently used key - i.e. the oldest key.
         *
         * https://leetcode.com/problems/lru-cache/
         *
         * Input
         * ["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
         * [[2], [1, 1],[2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
         * Output
         * [null, null, null, 1, null, -1, null, -1, 3, 4]
         */

        class LRUCache(val capacity: Int) {

            private val cache = LinkedHashMap<Int, Int>()

            fun get(key: Int): Int? {
                return cache[key]
            }

            fun put(key: Int, value: Int) {
                cache[key] = value

                while (cache.size > capacity) {
                    cache.remove(cache.iterator().next().key)
                }
            }
        }
    }
}