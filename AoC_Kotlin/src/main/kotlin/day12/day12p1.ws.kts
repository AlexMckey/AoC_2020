import day12.Day12.PathState
import day12.Day12.direction

val s = "F10\n" +
        "N3\n" +
        "F7\n" +
        "R90\n" +
        "F11"
val a = s.lines().map { it.first() to it.drop(1).toInt() }
a
var cur = PathState()
a.forEach{ cur = when(it.first) {
    'F' -> cur.move(it.second, cur.dir)
    'R' -> cur.rotate(it.second, clockwise = true)
    'L' -> cur.rotate(it.second, clockwise = false)
    else -> cur.move(it.second, it.first.direction())
    }
    println(cur)
}
cur
cur.point.manhattanDistance()