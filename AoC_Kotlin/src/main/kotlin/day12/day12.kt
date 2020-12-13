package day12

import AoCLib.*

object Day12:SomeDay(2020,12) {
    override fun first(data: String): Any? {
        var cur = PathState()
        data.toStrs().map { it.first() to it.drop(1).toInt() }
            .forEach{ cur = when(it.first) {
                'N' -> cur.toN(it.second)
                'E' -> cur.toE(it.second)
                'S' -> cur.toS(it.second)
                'W' -> cur.toW(it.second)
                'F' -> cur.forw(it.second)
                'R' -> cur.rotR(it.second)
                'L' -> cur.rotL(it.second)
                else -> cur
            } }
        return cur.p.manhattanDistance()
    } // 1106 Time: 27ms

    private fun rotate(navPoint: PathState, angle: Int, clockwise: Boolean = true): PathState {
        if (!clockwise) return rotate(navPoint,360 - angle)
        val newDir = navPoint.rotR(angle).d
        val newPoint = when (angle % 360) {
            90 -> Point(navPoint.p.y,-navPoint.p.x)
            180 -> Point(-navPoint.p.x,-navPoint.p.y)
            270 -> Point(-navPoint.p.y,navPoint.p.x)
            else -> Point(navPoint.p.x,navPoint.p.y)}
        return PathState(newPoint,newDir)
    }

    override fun second(data: String): Any? {
        var curPos = PathState()
        var navPoint = PathState()//PathState(Point(10,1))
        data.toStrs().map { it.first() to it.drop(1).toInt() }
            .forEach { when(it.first) {
                'N' -> navPoint = navPoint.toN(it.second)
                'E' -> navPoint = navPoint.toE(it.second)
                'S' -> navPoint = navPoint.toS(it.second)
                'W' -> navPoint = navPoint.toW(it.second)
                'F' -> curPos = PathState(curPos.p + navPoint.p * it.second, curPos.d)
                'R' -> navPoint = rotate(navPoint,it.second,true)
                'L' -> navPoint = rotate(navPoint,it.second,false)
                else -> {} }
            }
        return curPos.p.manhattanDistance()
    } // 107281
}

fun main() = SomeDay.mainify(Day12)