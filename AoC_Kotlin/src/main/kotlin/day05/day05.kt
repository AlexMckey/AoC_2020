package day05

import AoCLib.InputTransform.replaceAll
import AoCLib.SomeDay
import kotlin.math.max
import kotlin.math.min

object Day05: SomeDay(2020,5) {
    //fun calcSeatID(s: String): Int {
    //    fun posToKoef(ch: Char): Int = if (ch == 'B' || ch == 'R') 1 else -1
    //    fun calcPos(l: String, seed: Int): Int  =
    //        l.fold(seed to seed) {(acc,seed), i -> acc + seed / 2 * posToKoef(i) to seed / 2}.first - 1
    //    return 8 * calcPos(s.take(7),128) / 2 + calcPos(s.drop(7),8) / 2
    //}
    private fun calcSeatID(s: String): Int =
        s.reversed()
            .fold(1 to 0){ (pow2, acc), c -> pow2 * 2 to acc + (if (c == 'B' || c =='R') 1 else 0) * pow2 }
            .second

    // not optimized - sort is too long
//    override fun first(data: String): Any? {
//        return calcSeatID(data
//            .replaceAll(listOf('L' to 'F', 'R' to 'B'))
//            .split('\n').sorted().first())
//    } // 320 Time: 16ms

    // optimized
    override fun first(data: String): Any? {
        return data.split('\n').maxOfOrNull(Day05::calcSeatID)
    } // 922 Time: 3ms

    // not optimized - sort is too long
//    override fun second(data: String): Any? {
//        val seatIDs = data
//            .replaceAll(listOf('L' to 'F', 'R' to 'B'))
//            .split('\n').sorted()
//            .map(Day05::calcSeatID)
//        val holeIdx = seatIDs.zipWithNext(Int::minus).indexOf(2)
//        return seatIDs.first()-holeIdx-1
//    } // 747 Time: 15ms

    // optimized
    override fun second(data: String): Any? {
        return data.split('\n')
            .fold(Triple(Int.MAX_VALUE,Int.MIN_VALUE,0)){ (min, max, sum), s ->
                val seatID = calcSeatID(s)
                Triple(min(min,seatID), max(max,seatID), sum + seatID)
            }.let { res -> (res.second*(res.second + 1) - res.first*(res.first - 1))/2 - res.third }
    } // 747 Time: 1ms
}

fun main() = SomeDay.mainify(Day05)