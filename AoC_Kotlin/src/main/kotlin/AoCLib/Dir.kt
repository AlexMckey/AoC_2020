package AoCLib

import AoCLib.Dir.Companion.angleToDir

enum class Dir { N, E, S, W;
    companion object {
        fun angleToDir(angle: Int) =
            values()[((360 + angle) / 90) % values().size]
    }
}

fun Dir.toPath() = when (this) {
    Dir.N -> Point.toU
    Dir.S -> Point.toD
    Dir.W -> Point.toL
    else -> Point.toR
}
fun Dir.toAngle() = when (this) {
    Dir.N -> 0
    Dir.E -> 90
    Dir.S -> 180
    Dir.W -> 270
}

data class PathState(val p: Point = Point.Zero, val d: Dir = Dir.E) {
    fun toE(v: Int) = PathState(p+Point.toR*v,d)
    fun toW(v: Int) = PathState(p+Point.toL*v,d)
    fun toN(v: Int) = PathState(p+Point.toU*v,d)
    fun toS(v: Int) = PathState(p+Point.toD*v,d)
    fun forw(v: Int) =  PathState(p+d.toPath()*v,d)
    fun rotR(v: Int) = PathState(p,angleToDir(d.toAngle() + v))
    fun rotL(v: Int) = PathState(p,angleToDir(d.toAngle() - v))
}
