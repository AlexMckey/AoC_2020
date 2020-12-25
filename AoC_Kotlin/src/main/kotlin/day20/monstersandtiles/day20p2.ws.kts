import java.io.File
import kotlin.math.sqrt

val day = 20
val year = 2020
val inputDir = "d:\\YandexDisk\\DevsExercises\\AdventOfCode\\$year\\PuzzleInput\\"
val filepath = inputDir + "input${day.toString().padStart(2,'0')}sample.txt"
val data = File(filepath).bufferedReader().readText().trim()
val s = data.split("\r\n\r\n")

val tiles = s
    .flatMap { toTile(it).allTransformations() }
    .also { tiles ->
        tiles.forEach { findAdjacent(it,tiles) }
}

enum class Borders { U, D, L, R }

data class Tile(val id: Int, val data: List<String>) {
    val adjacent = mutableMapOf<Borders,Tile>()

    val borders = mapOf(
        Borders.U to data.first(),
        Borders.D to data.last(),
        Borders.L to data.joinToString(""){ it.first().toString() },
        Borders.R to data.joinToString(""){ it.last().toString() },)

    val borderLess get() = data.drop(1).dropLast(1)
        .map { it.drop(1).dropLast(1) }

    private fun hFlip(a: List<String>): List<String> = a.reversed()
    private fun vFlip(a: List<String>): List<String> = a.map { it.reversed() }
    private fun hvFlip(a: List<String>): List<String> = a.map { it.reversed() }.reversed()
    private fun rRot(a: List<String>) = a.flatMap { it.withIndex() }
        .groupBy({ (i, _) -> i }, { (_, v) -> v })
        .map { (_, v) -> v.reversed().joinToString("") }

    fun allTransformations(): List<Tile> = listOf(
        data, rRot(data), hvFlip(data), rRot(hvFlip(data)),
        hFlip(data), rRot(vFlip(data)), vFlip(data), rRot(hFlip(data)),)
        .map { Tile(id,it) }
}

val l1 = listOf("12","34")
Tile(0,l1).allTransformations()

fun toTile(s: String): Tile {
    val titleR = """Tile (\d+):""".toRegex()
    val lines = s.lines()
    val id = titleR.find(lines.first())!!.destructured.component1()
    val data = lines.drop(1)
    return Tile(id.toInt(), data)
}

fun oppositeBorder(border: Borders): Borders = when (border) {
    Borders.U -> Borders.D
    Borders.D -> Borders.U
    Borders.L -> Borders.R
    Borders.R -> Borders.L
}

fun findAdjacent(t: Tile, tiles: List<Tile>) =
    tiles.filterNot { t.id == it.id }.forEach { tile ->
        t.borders.forEach { (idBorder, border) ->
            if (border == tile.borders[oppositeBorder(idBorder)]!!)
                t.adjacent[idBorder] = tile
        }}

fun countMonsters(a: List<String>): Int {
    var monsters = 0
    for (i in a.indices) {
        for (j in a[i].indices) {
            if (hasMonsterOnIndex(a, i, j)) {
                monsters++
            }
        }
    }
    return monsters
}

val monster = listOf(
    "..................#.",
    "#....##....##....###",
    ".#..#..#..#..#..#...",
)

fun hasMonsterOnIndex(a: List<String>, i: Int, j: Int): Boolean {
    if (i + monster.lastIndex > a.lastIndex || j + monster.first().length - 1 > a.first().lastIndex) {
        return false
    }
    for (i2 in monster.indices) {
        for (j2 in monster[i2].indices) {
            if (monster[i2][j2] == '#' && a[i + i2][j + j2] != '#') {
                return false
            }
        }
    }
    return true
}

fun part1(): Long =
    tiles.filter { it.adjacent.size == 2 }
        .map { it.id.toLong() }
        .distinct()
        .reduce(Long::times)

tiles.filter { it.adjacent.size == 2 }.map { it.id.toLong() }.distinct()

part1()

fun part2(): Any {
    val imgSize = sqrt(tiles.size.toFloat() / 8).toInt()
    val img = Array(imgSize) { Array<Tile?>(imgSize) { null } }

    // find top left corner - tile that has two adjacent, right and bottom
    img[0][0] = tiles.first { it.adjacent.size == 2 &&
            it.adjacent[Borders.R] != null &&
            it.adjacent[Borders.D] != null }

    // fill first row by matching right border of the previous tile
    for (i in 1 until imgSize) {
        val prev = img[0][i-1]!!
        img[0][i] = tiles.first { current ->
            current.id == prev.adjacent[Borders.R]!!.id &&
                    current.borders[Borders.L]!! == prev.borders[Borders.R]!!
        }
    }
    // fill all other rows by matching bottom border of tiles in previous row
    for (j in 1 until imgSize) {
        for (i in 0 until imgSize) {
            val prev = img[j-1][i]!!
            img[j][i] = tiles.first { current ->
                current.id == prev.adjacent[Borders.D]!!.id &&
                        current.borders[Borders.U]!! == prev.borders[Borders.D]!!
            }
        }
    }

    val borderlessTileSize = tiles.first().borders[Borders.U]!!.length - 2
    val realImgSize = imgSize * borderlessTileSize
    val realImgCh = Array(realImgSize) { CharArray(realImgSize) }

    // create the real image
    for (i in 0 until imgSize) {
        for (j in 0 until imgSize) {
            img[i][j]!!.borderLess.forEachIndexed { i2, booleans ->
                booleans.forEachIndexed { j2, b ->
                    realImgCh[i * borderlessTileSize + i2][j * borderlessTileSize + j2] = b
                }
            }
        }
    }

    val realImg = realImgCh.map { it.joinToString("") }.toList()


    val totalHashes = realImg.sumBy { it.count { it == '#' } }
    val monsterHashes = monster.sumBy { it.count { it == '#' } }
    val monsterCount = Tile(0,realImg).allTransformations()
        .map { countMonsters(it.data) }
        .first { it > 0 }

    return totalHashes - monsterCount * monsterHashes
}

part2()