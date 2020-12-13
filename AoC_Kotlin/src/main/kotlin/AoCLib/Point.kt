package AoCLib

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.sqrt

data class Point(var x: Int = 0, var y: Int = 0) {
    override fun toString(): String = "[x:$x,y:$y]"
    operator fun plus(other: Point) = Point(other.x + x, other.y + y)
    operator fun plus(other: Pair<Int, Int>) = Point(other.first + x, other.second + y)
    operator fun minus(other: Point) = Point(other.x - x, other.y - y)
    operator fun plusAssign(other: Point) = run { x += other.x; y += other.y }
    operator fun minusAssign(other: Point) = run { x -= other.x; y -= other.y }
    operator fun unaryMinus() = Point(-x, -y)
    operator fun times(other: Int) = Point(x * other, y * other)
    operator fun times(other: Point) = Point(x * other.x, y * other.y)
    operator fun timesAssign(other: Int) = run { x *= other; y *= other }
    fun angle(other: Point) = atan2((other - this).y.toDouble(), (other - this).x.toDouble()) * 180 / PI
    fun distance(other: Point): Double {
        val delta = other - this
        return sqrt(1.0 * delta.x * delta.x + delta.y * delta.y)
    }

    fun manhattanDistance(p2: Point = Zero): Int {
        return abs(this.x - p2.y) + abs(this.y - p2.y)
    }

    fun near4(): List<Point> = listOf(this + toL, this + toR, this + toU, this + toD)
    fun near8(): List<Point> = listOf(this + toL, this + toR, this + toU, this + toD,
        this + toUL, this + toUR,this + toDL, this + toDR)

    companion object {
        fun toPoint(p: Pair<Int,Int>): Point = Point(p.first, p.second)
        fun Point.toPair(): Pair<Int,Int> = this.x to this.y
        val Zero = Point(0,0)
        val Start = Zero
        val toU = Point(0,1)
        val toD = Point(0,-1)
        val toL = Point(-1,0)
        val toR = Point(1,0)
        val toUL = toU + toL
        val toUR = toU + toR
        val toDR = toD + toR
        val toDL = toD + toL
    }
}

fun Point.toDirPoints(dir: Point) = generateSequence(this + dir) { it + dir }
fun Point.inBounds(xRange: IntRange, yRange: IntRange) = this.x in xRange && this.y in yRange
fun <T : Any> Point.inBounds(grid: ListGrid<T>) = grid.contains(this)
fun <T : Any> Point.inBounds(grid: MapGrid<T>) = grid.containsKey(this)