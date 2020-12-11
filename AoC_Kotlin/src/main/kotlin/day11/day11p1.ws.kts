import AoCLib.Point

val s = ("L.LL.LL.LL\n" +
        "LLLLLLL.LL\n" +
        "L.L.L..L..\n" +
        "LLLL.LL.LL\n" +
        "L.LL.LL.LL\n" +
        "L.LLLLL.LL\n" +
        "..L.L.....\n" +
        "LLLLLLLLLL\n" +
        "L.LLLLLL.L\n" +
        "L.LLLLL.LL").lines()
var seats = s
    .flatMapIndexed { y, s -> s
        .mapIndexed { x, ch -> Point(x,y) to ch } }
    .toMap()
    .filterValues { it != '.' }
seats
val curPos = Point.Start
seats[curPos] == 'L' && curPos.near8().mapNotNull { seats[it] }.all { it == 'L' }
seats[curPos] == '#' && curPos.near8().mapNotNull { seats[it] }.count { it == '#' } >= 4
val newSeats = seats
    .mapValues { kvp -> when {
        kvp.value == 'L' && kvp.key.near8().mapNotNull { seats[it] }.all { it == 'L' } -> '#'
        seats[curPos] == '#' && kvp.key.near8().mapNotNull { seats[it] }.count { it == '#' } >= 4 -> 'L'
        else -> kvp.value
    } }
newSeats
fun printSeats(xRange: IntRange, yRange: IntRange, seats: Map<Point,Char>) {
    for (y in yRange) {
        for (x in xRange)
            print(seats.getOrDefault(Point(x, y), '.'))
        println()
    }
}
printSeats(s.first().indices,s.indices,newSeats)