import AoCLib.Grid.toGrid
import AoCLib.Grid.bounds
import AoCLib.Point
import AoCLib.Point.Companion.inBounds
import AoCLib.Point.Companion.toD
import AoCLib.Point.Companion.toDL
import AoCLib.Point.Companion.toDR
import AoCLib.Point.Companion.toDirPoints
import AoCLib.Point.Companion.toL
import AoCLib.Point.Companion.toR
import AoCLib.Point.Companion.toU
import AoCLib.Point.Companion.toUL
import AoCLib.Point.Companion.toUR

val s = "L.LL.LL.LL\n" +
        "LLLLLLL.LL\n" +
        "L.L.L..L..\n" +
        "LLLL.LL.LL\n" +
        "L.LL.LL.LL\n" +
        "L.LLLLL.LL\n" +
        "..L.L.....\n" +
        "LLLLLLLLLL\n" +
        "L.LLLLLL.L\n" +
        "L.LLLLL.LL"
val s1 = ".......#.\n" +
        "...#.....\n" +
        ".#.......\n" +
        ".........\n" +
        "..#L....#\n" +
        "....#....\n" +
        ".........\n" +
        "#........\n" +
        "...#....."
val s2 = ".............\n" +
        ".L.L.#.#.#.#.\n" +
        "............."
val s3 = ".##.##.\n" +
        "#.#.#.#\n" +
        "##...##\n" +
        "...L...\n" +
        "##...##\n" +
        "#.#.#.#\n" +
        ".##.##."
val sl = s3.lines()
var seats = sl.toGrid()
    .filterValues { it != '.' }
seats
val curSeat = seats.filterValues { it == 'L' }.keys.first()
curSeat
val bnds = sl.bounds()
bnds
val ssl = curSeat.toDirPoints(toL).take(10)//.dropWhile{ !seats.containsKey(it) || it.inBounds(bnds.first,bnds.second) }.first()
ssl.map {  !seats.containsKey(it) }.toList()
ssl.map {  it.inBounds(bnds.first,bnds.second) }.toList()
fun near8Dirs(curPoint: Point, seats: Map<Point,Char>, bounds: Pair<IntRange,IntRange>): List<Point?> =
    listOf(toL, toR, toU, toD, toUL, toUR, toDL, toDR)
        .map { curPoint.toDirPoints(it)
            .find { seats.containsKey(it) ||
                    !it.inBounds(bounds.first,bounds.second) } }
curSeat.toDirPoints(toL).find { seats.containsKey(it) || !it.inBounds(bnds.first,bnds.second) }
curSeat.toDirPoints(toR).find{ seats.containsKey(it) || !it.inBounds(bnds.first,bnds.second) }
curSeat.toDirPoints(toU).find{ seats.containsKey(it) || !it.inBounds(bnds.first,bnds.second) }
curSeat.toDirPoints(toD).find{ seats.containsKey(it) || !it.inBounds(bnds.first,bnds.second) }
curSeat.toDirPoints(toUL).find{ seats.containsKey(it) || !it.inBounds(bnds.first,bnds.second) }
curSeat.toDirPoints(toUR).find{ seats.containsKey(it) || !it.inBounds(bnds.first,bnds.second) }
curSeat.toDirPoints(toDL).find{ seats.containsKey(it) || !it.inBounds(bnds.first,bnds.second) }
curSeat.toDirPoints(toDR).find{ seats.containsKey(it) || !it.inBounds(bnds.first,bnds.second) }
near8Dirs(curSeat,seats,bnds)
seats[curSeat] == 'L' && near8Dirs(curSeat,seats,bnds).mapNotNull { seats[it] }.all { it == 'L' }
seats[curSeat] == '#' && near8Dirs(curSeat,seats,bnds).mapNotNull { seats[it] }.count { it == '#' } >= 5