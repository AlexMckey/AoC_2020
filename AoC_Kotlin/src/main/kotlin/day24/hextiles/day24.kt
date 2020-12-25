package day24.hextiles

import AoCLib.SomeDay
import AoCLib.toStrs

object Day24:SomeDay(2020,24) {
    override val title = "Lobby Layout"

    data class HexPos(val x: Int = 0, val y: Int = 0, val z: Int = 0) {
        override fun toString(): String = "[x:$x,y:$y,z:$z]"
        operator fun plus(other: HexPos) = HexPos(other.x + x, other.y + y, other.z + z)
        fun near(): List<HexPos> = Dir.values().map { it.dirToCube() }.map { this + it }
    }
    enum class Dir {E, SE, SW, W, NW, NE;
        fun dirToCube(): HexPos = when (this) {
            E -> HexPos(1, -1, 0)
            W -> HexPos(-1, 1, 0)
            NE -> HexPos(1, 0, -1)
            SW -> HexPos(-1, 0, 1)
            NW -> HexPos(0, 1, -1)
            SE -> HexPos(0, -1, 1)
        }
    }
    val r = """e|se|sw|w|nw|ne""".toRegex()
    override fun first(data: String): Any? =
        data.toStrs().map { r.findAll(it).map {
            Dir.valueOf(it.value.toUpperCase()).dirToCube()}
            .reduce(HexPos::plus)
        }.groupingBy { it }.eachCount()
            .count { it.value % 2 == 1 }
        // 282 Time: 48ms

    private fun step(m: Map<HexPos,Int>): Map<HexPos,Int> =
        (m.keys
                + m.keys.flatMap { it.near() }
                    .minus(m.keys)
                    .groupingBy { it }.eachCount()
                    .filterValues { it == 2 }.keys
                - m.filterNot { it.key.near()
                    .intersect(m.keys).count() in 1..2}.keys)
            .map { it to 1}.toMap()

    override fun second(data: String): Any? {
        var blackTiles = data.toStrs().map { r.findAll(it).map {
            Dir.valueOf(it.value.toUpperCase()).dirToCube()}
            .reduce(HexPos::plus)
        }.groupingBy { it }.eachCount()
            .filterValues { it % 2 == 1 }
        repeat(100) {
            blackTiles = step(blackTiles)
        }
        return blackTiles.size
    } // 3445 Time: 381ms
}

fun main() = SomeDay.mainify(Day24)