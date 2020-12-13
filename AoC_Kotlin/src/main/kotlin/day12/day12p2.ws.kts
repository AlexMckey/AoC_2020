import kotlin.math.abs

data class Point(var x: Int = 0, var y: Int = 0) {
    override fun toString(): String = "[x:$x,y:$y]"
    operator fun plus(other: Point) = Point(other.x + x, other.y + y)
    operator fun plus(other: Pair<Int, Int>) = Point(other.first + x, other.second + y)
    operator fun minus(other: Point) = Point(other.x - x, other.y - y)
    operator fun unaryMinus() = Point(-x, -y)
    operator fun times(other: Int) = Point(x * other, y * other)
    operator fun times(other: Point) = Point(x * other.x, y * other.y)
    fun rotR() = Point(y,-x)
    fun rotL() = Point(-y,x)
    fun turnBack() = -this
    fun manhattanDistance(p2: Point = Zero): Int {
        return abs(this.x - p2.y) + abs(this.y - p2.y)
    }

    companion object {
        val Zero = Point(0,0)
        val toU = Point(0,1)
        val toD = Point(0,-1)
        val toL = Point(-1,0)
        val toR = Point(1,0)
    }
}
enum class Direction(val p: Point) { N(Point.toU), E(Point.toR), S(Point.toD), W(Point.toL)}
fun direction(ch: Char) = Direction.valueOf(ch.toString())

data class PathState(val point: Point = Point.Zero, val dir: Direction = Direction.E) {
    fun move(v: Int, d: Direction) = PathState(point + d.p * v, dir)
    fun rotate(v: Int, clockwise: Boolean = true /* R */) =
        PathState(point, Direction.values()[(dir.ordinal + (v / 90 * if(clockwise) 1 else 3)) % 4])
}

val s = "F10\n" +
        "N3\n" +
        "F7\n" +
        "R90\n" +
        "F11"
val a = s.lines().map { it.first() to it.drop(1).toInt() }
a
fun navRotate(navPoint: PathState, angle: Int, clockwise: Boolean = true): PathState {
    if (!clockwise) return navRotate(navPoint,360 - angle)
    var np = navPoint.point
    repeat(angle / 90) { np = np.rotR() }
    return PathState(np, navPoint.dir)
}
var navPoint = PathState(Point(10,1))
var curPos = PathState()
a.forEach { when(it.first) {
    'R' -> navPoint = navRotate(navPoint, it.second, clockwise = true)
    'L' -> navPoint = navRotate(navPoint, it.second, clockwise = false)
    'F' -> curPos = PathState(curPos.point + navPoint.point * it.second, curPos.dir)
    else -> navPoint = navPoint.move(it.second, direction(it.first)) }
    println("cur: $curPos   nav: $navPoint")
}
curPos
curPos.point.manhattanDistance()