import AoCLib.PathState

val s = "F10\n" +
        "N3\n" +
        "F7\n" +
        "R90\n" +
        "F11"
val a = s.lines().map { it.first() to it.drop(1).toInt() }
a
var cur = PathState()
a.forEach{ cur = when(it.first) {
    'N' -> cur.toN(it.second)
    'E' -> cur.toE(it.second)
    'S' -> cur.toS(it.second)
    'W' -> cur.toW(it.second)
    'F' -> cur.forw(it.second)
    'R' -> cur.rotR(it.second)
    'L' -> cur.rotL(it.second)
    else -> cur }
    println(cur)
}
cur
cur.p.manhattanDistance()