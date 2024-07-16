package com.rcs.leetcode

fun main() {
    val myQueue = LC232_QueueUsingStacks.Companion.MyQueue()

    myQueue.push(1)
    myQueue.push(2)
    myQueue.push(3)

    println(myQueue.peek())
    println(myQueue.empty())
    println(myQueue.pop())
    println(myQueue.empty())
    println(myQueue.pop())
    println(myQueue.empty())
    println(myQueue.pop())
    println(myQueue.empty())
}

class LC232_QueueUsingStacks {

    companion object {

        /**
         * https://leetcode.com/problems/implement-queue-using-stacks/
         */

        class MyQueue() {

            private var stack = ArrayDeque<Int>()

            fun push(x: Int) {
                val auxiliaryStack = ArrayDeque<Int>()

                while (stack.isNotEmpty()) {
                    auxiliaryStack.add(stack.removeLast())
                }

                auxiliaryStack.add(x)

                while (auxiliaryStack.isNotEmpty()) {
                    stack.add(auxiliaryStack.removeLast())
                }
            }

            fun pop(): Int {
                return stack.removeLast()
            }

            fun peek(): Int {
                return stack.last()
            }

            fun empty(): Boolean {
                return stack.isEmpty()
            }
        }
    }
}