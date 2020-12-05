package day05

import InputLib.DayInput
import kotlin.math.max
import kotlin.math.min

//fun calcSeatID(s: String): Int {
//    fun posToKoef(ch: Char): Int = if (ch == 'B' || ch == 'R') 1 else -1
//    fun calcPos(l: String, seed: Int): Int  =
//        l.fold(seed to seed) {(acc,seed), i -> acc + seed / 2 * posToKoef(i) to seed / 2}.first - 1
//    return 8 * calcPos(s.take(7),128) / 2 + calcPos(s.drop(7),8) / 2
//}
fun calcSeatID(s: String): Int =
    s.reversed()
        .fold(1 to 0){ (pow2, acc), c -> pow2 * 2 to acc + (if (c == 'B' || c =='R') 1 else 0) * pow2 }
        .second

fun main() {
    val rawData = DayInput.inputStr("input05.txt")
        .replace("L","F")
        .replace("R","B")
        .split("\n").dropLast(1)
    val seatData = rawData.sorted()
    val res1 = calcSeatID(seatData.first())
    println(res1) // 922
    val res1Opt = DayInput.inputStr("input05.txt").split('\n').dropLast(1)
        .maxOfOrNull { calcSeatID(it) }
    println(res1Opt) // 922
    val seatIDs = seatData.map {calcSeatID(it)}
    val holeIdx = seatIDs.zipWithNext(Int::minus).indexOf(2)
    val res2 = seatIDs.first()-holeIdx-1
    println(res2) // 747
    val res2Opt = rawData.fold(Triple(Int.MAX_VALUE,Int.MIN_VALUE,0)){ (min, max, sum), s ->
        val seatID = calcSeatID(s)
        Triple(min(min,seatID), max(max,seatID), sum + seatID) }
    println((res2Opt.second*(res2Opt.second + 1) - res2Opt.first*(res2Opt.first - 1))/2 - res2Opt.third) // 747
}