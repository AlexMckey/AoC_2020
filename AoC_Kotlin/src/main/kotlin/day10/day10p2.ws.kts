import AoCLib.Collections.series
import AoCLib.Math.trib

val s1 = "16\n" +
        "10\n" +
        "15\n" +
        "5\n" +
        "1\n" +
        "11\n" +
        "7\n" +
        "19\n" +
        "6\n" +
        "12\n" +
        "4"
val d1_ = s1.split('\n').map { it.toInt() }
val d1 = d1_ + 0
val r1 = d1.sorted().zipWithNext { a, b -> b - a }
r1
val v1 = r1.series().map { if (it.first() == 1) it.size-1 else 0 }
v1
v1.map { 1 shl it }.reduce(Int::times)
val s2 = "28\n" +
        "33\n" +
        "18\n" +
        "42\n" +
        "31\n" +
        "14\n" +
        "46\n" +
        "20\n" +
        "48\n" +
        "47\n" +
        "24\n" +
        "23\n" +
        "49\n" +
        "45\n" +
        "19\n" +
        "38\n" +
        "39\n" +
        "11\n" +
        "1\n" +
        "32\n" +
        "25\n" +
        "35\n" +
        "8\n" +
        "17\n" +
        "7\n" +
        "9\n" +
        "4\n" +
        "2\n" +
        "34\n" +
        "10\n" +
        "3"
val d2_ = s2.split('\n').map { it.toInt() }
val d2 = d2_ + 0
d2.sorted().zipWithNext { a, b -> b - a }.groupingBy { it }.eachCount()
val r2 = d2.sorted().zipWithNext { a, b -> b - a }
r2
r2.series()
val v2 = r2.series().map { if (it.first() == 1) it.size-1 else 0 }
v2
v2.map { 1 shl it }.map { if (it == 8) 7 else it }.reduce(Int::times)
fun fibonacciSeq() = sequence {
    var prevCur = Triple(1L,1L,2L)
    while (true) {
        yield (prevCur.first)
        prevCur = Triple(prevCur.second, prevCur.third, prevCur.first + prevCur.second + prevCur.third)
    }
}
fun fib(n: Int) = fibonacciSeq().drop(n).first()
trib(5)