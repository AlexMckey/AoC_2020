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
