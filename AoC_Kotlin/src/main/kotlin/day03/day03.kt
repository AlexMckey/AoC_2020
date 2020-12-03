package day03

import InputLib.DayInput

data class Point(val x: Int, val y: Int) {
    override fun toString(): String = "[x:$x,y:$y]"
//    operator fun plus(other: Point) = Point(other.x + x, other.y + y)
    operator fun plus(other: Pair<Int, Int>) = Point(other.first + x, other.second + y)
}

fun main() {
    val area = DayInput.inputStrsList("input03.txt")
    val height = area.size
    val weight = area.first().length
    val start = Point(0,0)
    val angle = 3 to 1
    fun points(angle: Pair<Int,Int>) = generateSequence(start) { it + angle }.takeWhile { it.y < height }.toList()
    val idxs = points(angle)
    val res1 = idxs.count { area[it.y][it.x % weight] == '#' }
    println(res1) // 162
    val angles = listOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2)
    val res2 = angles.map { points(it).count { area[it.y][it.x % weight] == '#' } }
        .map { it.toLong() }.reduce(Long::times)
    println(res2) // 3064612320
}