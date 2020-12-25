package day12.ferry

import AoCLib.*

object Day12:SomeDay(2020,12) {
    override val title = "Rain Risk"

    enum class Direction(val p: Point) { N(Point.toU), E(Point.toR), S(Point.toD), W(Point.toL)}
    fun Char.direction() = Direction.valueOf(this.toString())

    data class PathState(val point: Point = Point.Zero, val dir: Direction = Direction.E) {
        fun move(v: Int, d: Direction) = PathState(point + d.p * v, dir)
        fun rotate(v: Int, clockwise: Boolean = true /* R */) =
            PathState(point, Direction.values()[(dir.ordinal + (v / 90 * if(clockwise) 1 else 3)) % 4])
    }

    override fun first(data: String): Any? {
        var cur = PathState()
        data.toStrs().map { it.first() to it.drop(1).toInt() }
            .forEach{ cur = when(it.first) {
                'F' -> cur.move(it.second, cur.dir)
                'R' -> cur.rotate(it.second, clockwise = true)
                'L' -> cur.rotate(it.second, clockwise = false)
                else -> cur.move(it.second, it.first.direction())
            } }
        return cur.point.manhattanDistance()
    } // 1106 Time: 27ms

    fun navRotate(navPoint: PathState, angle: Int, clockwise: Boolean = true): PathState {
        if (!clockwise) return navRotate(navPoint,360 - angle)
        var np = navPoint.point
        repeat(angle / 90) { np = np.rotR() }
        return PathState(np, navPoint.dir)
    }

    override fun second(data: String): Any? {
        var curPos = PathState()
        var navPoint = PathState(Point(10,1))
        data.toStrs().map { it.first() to it.drop(1).toInt() }
            .forEach { when(it.first) {
                'R' -> navPoint = navRotate(navPoint, it.second, clockwise = true)
                'L' -> navPoint = navRotate(navPoint, it.second, clockwise = false)
                'F' -> curPos = PathState(curPos.point + navPoint.point * it.second, curPos.dir)
                else -> navPoint = navPoint.move(it.second, it.first.direction()) }
                println("$it   =>   cur: $curPos   nav: $navPoint")
            }
        return curPos.point.manhattanDistance()
    } // 107281 Time: 2ms
}

fun main() = SomeDay.mainify(Day12)