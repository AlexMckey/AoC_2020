package day09.weakness

import AoCLib.toLongs
import AoCLib.SomeDay
import AoCLib.headAndTail
import AoCLib.pairs
import AoCLib.sum
import kotlin.collections.ArrayDeque

object Day09: SomeDay(2020,9) {
    override val title = "Encoding Error"

    private fun firstInvalid(nums: List<Long>, preambleSize: Int = 25) =
        nums.asSequence()
            .windowed(preambleSize+1)
            .find { !it.take(preambleSize)
                .pairs()
                .map { it.sum() }
                .contains(it.last()) }
            ?.last()

    private fun checkWindow(preamble: List<Long>, preambleSize: Int, num: Long) =
        preamble.asSequence()
            .windowed(preambleSize)
            .find { it.sum() == num }
    private fun findSeqsForNum(preamble: List<Long>, preambleSize: Int, num: Long): List<Long>? {
        var i = 2
        var res: List<Long>?
        do {
            res = checkWindow(preamble,i,num)
            i++
        } while (res == null && i < preambleSize)
        return res
    }

    private fun contiguousSumRange(nums: Sequence<Long>, sum: Long): Sequence<Long> {
        tailrec fun helper(range: ArrayDeque<Long>, rangeSum: Long, suffix: List<Long>): Sequence<Long> {
            return if (rangeSum == sum && range.size > 1)
                range.asSequence()
            else if (rangeSum < sum) {
                val (x, newSuffix) = suffix.headAndTail()
                range.addFirst(x)
                helper(range, rangeSum + x, newSuffix)
            } else {
                val x = range.removeLast()
                helper(range, rangeSum - x, suffix)
            }
        }

        return helper(ArrayDeque(), 0, nums.toList())
    }

    override fun first(data: String): Any? {
        return firstInvalid(data.toLongs(),25)
    } // 85848519 Time: 47ms

    override fun second(data: String): Any? {
        val seqs = data.toLongs()
        val num = firstInvalid(seqs,25) ?: 0
        //val res1 = contiguousSumRange(seqs.asSequence(),num)
        val res = findSeqsForNum(seqs,25, num) ?: return null
        return res.maxOrNull()!! + res.minOrNull()!!
        //return res1.maxOrNull()!! + res1.minOrNull()!!
    } // 13414198 Time: 22ms
}

fun main() = SomeDay.mainify(Day09)