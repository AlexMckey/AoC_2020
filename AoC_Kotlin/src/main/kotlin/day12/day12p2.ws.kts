import AoCLib.PathState
import AoCLib.Point
import AoCLib.toAngle

val s = "F10\n" +
        "N3\n" +
        "F7\n" +
        "R90\n" +
        "F11"
val a = s.lines().map { it.first() to it.drop(1).toInt() }
a
fun rotate(navPoint: PathState, angle: Int, clockwise: Boolean = true): PathState {
    if (!clockwise) return rotate(navPoint,360 - angle)
    val newDir = navPoint.rotR(angle).d
    val newPoint = when (angle % 360) {
        90 -> Point(navPoint.p.y,-navPoint.p.x)
        180 -> Point(-navPoint.p.x,-navPoint.p.y)
        270 -> Point(-navPoint.p.y,navPoint.p.x)
        else -> Point(navPoint.p.x,navPoint.p.y)}
    return PathState(newPoint,newDir)
}
var navPoint = PathState(Point(10,1))
var curPos = PathState()
a.forEach{ when(it.first) {
    'N' -> navPoint = navPoint.toN(it.second)
    'E' -> navPoint = navPoint.toE(it.second)
    'S' -> navPoint = navPoint.toS(it.second)
    'W' -> navPoint = navPoint.toW(it.second)
    'F' -> curPos = PathState(curPos.p + navPoint.p * it.second, curPos.d)
    'R' -> navPoint = rotate(navPoint,it.second,true)
    'L' -> navPoint = rotate(navPoint,it.second,false)
    else -> {} }
    println("nav=$navPoint : pos=$curPos")
}
curPos
curPos.p.manhattanDistance()