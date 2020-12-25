package day25

import AoCLib.SomeDay
import AoCLib.toStrs

object Day25: SomeDay(2020,25) {
    private fun calcLoopSize(pubKey: Int, subj: Int, mod: Int = 20201227): Int =
        generateSequence(1 to 1) {
            it.first+1 to it.second * subj % mod
        }.dropWhile { it.second != pubKey }.first().first-1
    private fun findKey(subj: Int, loopSize: Int, mod: Int = 20201227): Long =
        generateSequence(1L) { it * subj % mod }.drop(loopSize).first()

    override fun first(data: String): Any? {
        val (cardPK, doorPK) = data.toStrs().map { it.toInt() }
        val loopSize = calcLoopSize(doorPK, 7)
        return findKey(cardPK, loopSize)
    }
}

fun main() = SomeDay.mainify(Day25)