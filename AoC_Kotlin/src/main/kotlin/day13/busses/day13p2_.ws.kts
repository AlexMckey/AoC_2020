import AoCLib.headAndTail
import AoCLib.lcm
import java.math.BigInteger

val s1 = "7,13,x,x,59,x,31,19"
val s2 = "17,x,13,19"
val s3 = "67,7,59,61"
val s4 = "67,x,7,59,61"
val s5 = "67,7,x,59,61"
val s6 = "1789,37,47,1889"
val bs = s6.split(',')
        .withIndex()
        .filter { it.value != "x" }
        .map { it.value.toBigInteger() to it.index }
fun gcd(a: BigInteger, b: BigInteger): BigInteger {
    var (ta, tb) = (minOf(a.abs(), b.abs()) to maxOf(a.abs(), b.abs()))
    while (ta != BigInteger.ZERO) {
        val na = tb % ta
        tb = ta
        ta = na
    }
    return tb
}
fun lcf(a: BigInteger, b: BigInteger): BigInteger {
    return a * b / gcd(a, b)
}
bs
val r = BigInteger.valueOf(1202161486)
bs.map { r.mod(it.first) to it.second }
val res = bs.drop(1).fold(bs.first().first){ acc, pair ->
    lcf(acc-pair.second.toBigInteger(),pair.first)
}
res
r - res
val busses = bs.map { it.second to it.first.toLong() }
var step = busses[0].second
var nextBus = 1
var timestamp = 0L
do {
    timestamp += step
    if ((timestamp + busses[nextBus].first) % busses[nextBus].second == 0L)
        step = lcm(step, busses[nextBus++].second)
} while (nextBus < busses.size)
timestamp
tailrec fun recCalc(bs: List<Pair<Int,Long>>, st: Long, ts: Long): Long {
    if (bs.isEmpty()) return ts
    val (h,t) = bs.headAndTail()
    val (i,n) = h
    return if ((ts+i) % n == 0L)
        recCalc(t,lcm(st,n),ts)
    else recCalc(bs,st,ts+st)
}
recCalc(busses.drop(1),busses[0].second,busses[0].first.toLong())