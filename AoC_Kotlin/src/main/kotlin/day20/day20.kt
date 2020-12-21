package day20

import AoCLib.SomeDay
import AoCLib.splitByBlankLines
import kotlin.math.sqrt

typealias Grid = List<String>

enum class Borders{ U, D, L, R }

data class Tile(val title: Int, val data: Grid) {
    override fun toString() =
        "Tile $title:\n" + data.joinToString("\n")

    val adjacent = mutableMapOf<Borders,Tile>()

    val borders = mapOf(
        Borders.U to data.first(),
        Borders.D to data.last(),
        Borders.L to data.joinToString(""){ it.first().toString() },
        Borders.R to data.joinToString(""){ it.last().toString() },)

    private val edges = borders.map { it.value }
    val allEdges = edges + edges.map { it.reversed() }

    companion object {
        fun parse(s: String): Tile {
            val titleR = """Tile (\d+):""".toRegex()
            val lines = s.lines()
            val title = titleR.find(lines.first())!!.destructured.component1()
            return Tile(title.toInt(),lines.drop(1))
        }
    }

    private fun hFlip(a: Grid): Grid = a.reversed()
    private fun vFlip(a: Grid): Grid = a.map { it.reversed() }
    private fun hvFlip(a: Grid): Grid = a.map { it.reversed() }.reversed()
    private fun rRot(a: Grid) = a.flatMap { it.withIndex() }
        .groupBy({ (i, _) -> i }, { (_, v) -> v })
        .map { (_, v) -> v.reversed().joinToString("") }

    fun allTransformations(): List<Tile> = listOf(
        data, rRot(data), hvFlip(data), rRot(hvFlip(data)),
        hFlip(data), rRot(vFlip(data)), vFlip(data), rRot(hFlip(data)),)
        .map { Tile(title,it) }

    private fun oppositeBorder(border: Borders): Borders = when (border) {
        Borders.U -> Borders.D
        Borders.D -> Borders.U
        Borders.L -> Borders.R
        Borders.R -> Borders.L
    }

    fun findAdjacent(tiles: List<Tile>) =
        tiles.filterNot { title == it.title }.forEach { tile ->
            borders.forEach { (idBorder, border) ->
                if (border == tile.borders[oppositeBorder(idBorder)]!!)
                    adjacent[idBorder] = tile
            }}

    fun countMonsters(monster: Tile): Int {
        fun hasMonsterOnIndex(a: Grid, i: Int, j: Int): Boolean {
            if (i + monster.data.lastIndex > a.lastIndex ||
                j + monster.data.first().length - 1 > a.first().lastIndex) {
                return false
            }
            for (i2 in monster.data.indices) {
                for (j2 in monster.data[i2].indices) {
                    if (monster.data[i2][j2] == '#' && a[i + i2][j + j2] != '#') {
                        return false
                    }
                }
            }
            return true
        }
        var monsters = 0
        for (i in data.indices) {
            for (j in data[i].indices) {
                if (hasMonsterOnIndex(data, i, j)) {
                    monsters++
                }
            }
        }
        return monsters
    }

    val totalHashes = data.sumBy { it.count { it == '#' } }
    val borderLess get() = data.drop(1).dropLast(1)
        .map { it.drop(1).dropLast(1) }

}

object Day20:SomeDay(2020,20) {
    override fun first(data: String): Any? =
        data.splitByBlankLines()
            .map { Tile.parse(it) }
            .flatMap { tile -> tile.allEdges
                .map { border -> tile.title to border } }
            .groupBy({it.second},{it.first})
            .filterValues { it.size >= 2 }
            .values
            .flatten()
            .groupingBy { it }
            .eachCount()
            .filterValues { it == 4 }
            .keys
            .map { it.toLong() }
            .reduce(Long::times)
    // 64802175715999 Time: 58ms

    override fun second(data: String): Any? {
        val tiles = data.splitByBlankLines()
            .flatMap { Tile.parse(it).allTransformations() }
            .also { tiles ->
                tiles.forEach { it.findAdjacent(tiles) }
            }

        val imgSize = sqrt(tiles.size.toFloat() / 8).toInt()
        val img = Array(imgSize) { Array<Tile?>(imgSize) { null } }

        img[0][0] = tiles.first { it.adjacent.size == 2 &&
                it.adjacent[Borders.R] != null &&
                it.adjacent[Borders.D] != null }

        for (i in 1 until imgSize) {
            val prev = img[0][i-1]!!
            img[0][i] = tiles.first { current ->
                current.title == prev.adjacent[Borders.R]!!.title &&
                        current.borders[Borders.L]!! == prev.borders[Borders.R]!!
            }
        }
        // fill all other rows by matching bottom border of tiles in previous row
        for (j in 1 until imgSize) {
            for (i in 0 until imgSize) {
                val prev = img[j-1][i]!!
                img[j][i] = tiles.first { current ->
                    current.title == prev.adjacent[Borders.D]!!.title &&
                            current.borders[Borders.U]!! == prev.borders[Borders.D]!!
                }
            }
        }

        val borderlessTileSize = tiles.first().borders[Borders.U]!!.length - 2
        val realImgSize = imgSize * borderlessTileSize
        val realImgCh = Array(realImgSize) { CharArray(realImgSize) }

        for (i in 0 until imgSize) {
            for (j in 0 until imgSize) {
                img[i][j]!!.borderLess.forEachIndexed { i2, booleans ->
                    booleans.forEachIndexed { j2, b ->
                        realImgCh[i * borderlessTileSize + i2][j * borderlessTileSize + j2] = b
                    }
                }
            }
        }

        val realImg = Tile(0,realImgCh.map { it.joinToString("") }.toList())

        val monster = Tile(1,listOf(
            "..................#.",
            "#....##....##....###",
            ".#..#..#..#..#..#...",
        ))

        val monsterCount = realImg.allTransformations()
            .map { it.countMonsters(monster) }
            .first { it > 0 }

        return realImg.totalHashes - monsterCount * monster.totalHashes
    } // 2146 Time: 230ms
}

fun main() = SomeDay.mainify(Day20)