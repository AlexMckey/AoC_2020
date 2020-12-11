package AoCLib

typealias ArrayGrid<T> = Array<Array<T>>

typealias IntGrid = Array<IntArray>
typealias LongGrid = Array<LongArray>
typealias CharGrid = Array<CharArray>

operator fun <T> ArrayGrid<T>.contains(p: Point) = inBounds(p.x,p.y)

operator fun <T> ArrayGrid<T>.contains(p: Pair<Int,Int>) = inBounds(p.first,p.second)

fun <T> ArrayGrid<T>.inBounds(x: Int, y: Int) =
    y in this.indices && x in this.first().indices