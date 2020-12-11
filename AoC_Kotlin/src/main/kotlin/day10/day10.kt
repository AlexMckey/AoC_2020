package day10

import AoCLib.toInts
import AoCLib.SomeDay
import AoCLib.series
import AoCLib.tribonacciSeq

object Day10:SomeDay(2020,10) {
    private fun joltDifferences(jolts: List<Int>): Int {
        val t = jolts + 0 + (jolts.maxOrNull()!! + 3)
        val res = t.sorted().zipWithNext { a, b -> b - a }.groupingBy { it }.eachCount()
        return res[1]!! * res[3]!!
    }
    override fun first(data: String): Any? =
        joltDifferences(data.toInts())
    // 1820 Time: 26ms

    private val tribNums = tribonacciSeq().take(7).mapIndexed { index, l -> index to l }.toMap()
    private fun countDistinctArrangements(jolts: List<Int>): Long =
        (jolts + 0).sorted().zipWithNext { a, b -> b - a }
            .series().map { if (it.first() == 1) it.size else 1 }
            .map {
                tribNums[it] ?: 0L
//                when (it) {
//                5 -> 13L
//                4 -> 7L
//                3 -> 4L
//                2 -> 2L
//                1 -> 1L
//                else -> 0L
//            }}
            }.reduce(Long::times)
    override fun second(data: String): Any? =
        countDistinctArrangements(data.toInts())
    // 3454189699072 Time: 2ms
}

fun main() = SomeDay.mainify(Day10)