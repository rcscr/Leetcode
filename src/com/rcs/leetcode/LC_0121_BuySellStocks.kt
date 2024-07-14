package com.rcs.leetcode

fun main() {
    println(LC_0121_BuySellStocks.maxProfit(intArrayOf(7,1,5,3,6,4)))
    println(LC_0121_BuySellStocks.maxProfit(intArrayOf(7,6,4,3,1)))
}

class LC_0121_BuySellStocks {

    companion object {

        /**
         * https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
         *
         * Example 1:
         * Input: prices = [7,1,5,3,6,4]
         * Output: 5
         *
         * Example 2:
         * Input: prices = [7,6,4,3,1]
         * Output: 0
         * Explanation: In this case, no transactions are done and the max profit = 0.
         */

        data class Transaction(val buyIndex: Int, val sellIndex: Int, val profit: Int)

        fun maxProfit(prices: IntArray): Int {
            return prices
                .foldIndexed(Transaction(0, 0, 0)) { index, transaction, price ->
                    val maxIndex = (index+1..<prices.size).maxByOrNull { prices[it] }
                    when (maxIndex != null && prices[maxIndex] - price > transaction.profit) {
                        true -> Transaction(index, maxIndex, prices[maxIndex] - price)
                        else -> transaction
                    }
                }
                .profit
        }
    }
}