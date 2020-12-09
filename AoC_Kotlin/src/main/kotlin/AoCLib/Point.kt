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
    operator fun timesAssign(other: Int) = run { x *= other; y *= other }
    fun angle(other: Point) = atan2((other - this).y.toDouble(), (other - this).x.toDouble()) * 180 / PI
    fun distance(other: Point): Double {
        val delta = other - this
        return sqrt(1.0 * delta.x * delta.x + delta.y * delta.y)
    }

    fun manhattanDistance(p2: Point = Zero): Int {
        return abs(this.x - p2.y) + abs(this.y - p2.y)
    }

    fun near4(): List<Point> = listOf(this + toLeft, this + toRight, this + toUp, this + toDown)
    fun near8(): List<Point> = listOf(this + toLeft, this + toRight, this + toUp, this + toDown,
        this + toLeft + toUp, this + toRight + toUp,this + toLeft + toDown, this + toRight + toDown)

    companion object {
        fun toPoint(p: Pair<Int,Int>): Point = Point(p.first, p.second)
        fun Point.toPair(): Pair<Int,Int> = this.x to this.y
        val Zero = Point(0,0)
        val Start = Zero
        val toLeft = Point(-1,0)
        val toRight = Point(1,0)
        val toUp = Point(0,1)
        val toDown = Point(0,-1)
        fun incXPoint(): (Point) -> Point = { p -> Point(p.x + 1, p.y) }
        fun decXPoint(): (Point) -> Point = { p -> Point(p.x - 1, p.y) }
        fun incYPoint(): (Point) -> Point = { p -> Point(p.x, p.y + 1) }
        fun decYPoint(): (Point) -> Point = { p -> Point(p.x, p.y - 1) }
    }
}

fun List<String>.toGrid() = this
    .flatMapIndexed { col, line ->
        line.mapIndexed { row, char ->
            Point(row, col) to char
        }
    }
    .associate { it }