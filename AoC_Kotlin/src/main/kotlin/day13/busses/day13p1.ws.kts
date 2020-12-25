import AoCLib.sum

val s = "939\n" +
        "7,13,x,x,59,x,31,19"
val ss = s.lines()
val ts = ss.first().toInt()
val bus = ss.last().split(',').filter { it != "x" }.map{ it.toInt() }
ts
bus
val res = bus.map { it to (ts / it + 1) * it - ts }.minByOrNull { it.second }!!
res.first * res.second
