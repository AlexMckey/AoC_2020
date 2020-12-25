package day13.busses

import AoCLib.SomeDay
import AoCLib.headAndTail
import AoCLib.lcm
import AoCLib.toStrs

object Day13:SomeDay(2020,13) {
    override val title = "Shuttle Search"

    override fun first(data: String): Any? {
        val (ts, bs) = data.toStrs().let { it.first().toInt() to it.last()
            .split(',').filter { it != "x" }.map { it.toInt() }}
        val res = bs.map { it to (ts / it + 1) * it - ts }
            .minByOrNull { it.second }!!
        return res.first * res.second
    } // 2305 Time: 21ms

    private tailrec fun calcTimestamp(busses: List<Pair<Int,Long>>, step: Long, timestamp: Long): Long {
        if (busses.isEmpty()) return timestamp
        val (bus,tail) = busses.headAndTail()
        val (idx,busID) = bus
        return if ((timestamp + idx) % busID == 0L)
            calcTimestamp(tail,lcm(step, busID),timestamp)
        else calcTimestamp(busses,step,timestamp + step)
    }

    override fun second(data: String): Any? {
        val busses = data.toStrs().last().split(',')
                .withIndex()
                .filter { it.value != "x" }
                .map { it.index to it.value.toLong() }
        return calcTimestamp(busses.drop(1), busses.first().second, busses.first().first.toLong())
    } // 552612234243498 Time: 3ms
}

fun main() = SomeDay.mainify(Day13)