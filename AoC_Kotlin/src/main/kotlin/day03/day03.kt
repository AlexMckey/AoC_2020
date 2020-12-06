package day03

import AoCLib.InputTransform.toStrs
import AoCLib.SomeDay
import AoCLib.Point

object Day03: SomeDay(2020,3) {
    private fun points(fromPoint: Point, angle: Point) = generateSequence(fromPoint) { it + angle }
    private fun pointsByAngle(area: List<String>, angle: Point): List<Char> {
        val height = area.size
        val weight = area.first().length
        val idxs = points(Point.Start, angle).takeWhile { it.y < height }
        return idxs.map { area[it.y][it.x % weight] }.toList()
    }

    override fun first(data: String): Any? {
        return pointsByAngle(data.toStrs(), Point(3,1)).count { it == '#' }
    } // 162 Time: 29ms

    override fun second(data: String): Any? {
        val area = data.toStrs()
        val angles = listOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2).map(Point::toPoint)
        return angles.map{ pointsByAngle(area,it).count { it == '#' } }
            .map { it.toLong() }.reduce(Long::times)
    } // 3064612320 Time: 3ms
}

fun main() = SomeDay.mainify(Day03)