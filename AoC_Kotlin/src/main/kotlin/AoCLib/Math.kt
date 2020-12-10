package AoCLib

import kotlin.math.abs

// useful math functions

fun gcd(a: Int, b: Int): Int {
    var (ta, tb) = (minOf(abs(a), abs(b)) to maxOf(abs(a), abs(b)))
    while (ta != 0) {
        val na = tb % ta
        tb = ta
        ta = na
    }
    return tb
}

fun gcd(a: Long, b: Long): Long {
    var (ta, tb) = (minOf(abs(a), abs(b)) to maxOf(abs(a), abs(b)))
    while (ta != 0L) {
        val na = tb % ta
        tb = ta
        ta = na
    }
    return tb
}

fun lcf(a: Int, b: Int): Int {
    return a * b / gcd(a, b)
}

fun lcf(a: Long, b: Long): Long {
    return a * b / gcd(a, b)
}

fun fibonacciSeq() = sequence {
    var prevCur = 1L to 1L
    while (true) {
        yield (prevCur.first)
        prevCur = prevCur.second to prevCur.first + prevCur.second
    }
}
fun fib(n: Int) = fibonacciSeq().drop(n).first()

fun tribonacciSeq() = sequence {
    var prevCur = Triple(1L,1L,2L)
    while (true) {
        yield (prevCur.first)
        prevCur = Triple(prevCur.second, prevCur.third, prevCur.first + prevCur.second + prevCur.third)
    }
}
fun trib(n: Int) = tribonacciSeq().drop(n).first()
