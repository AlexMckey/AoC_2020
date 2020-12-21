import java.io.File

val day = 20
val year = 2020
val inputDir = "d:\\YandexDisk\\DevsExercises\\AdventOfCode\\$year\\PuzzleInput\\"
val filepath = inputDir + "input${day.toString().padStart(2,'0')}sample.txt"
val data = File(filepath).bufferedReader().readText().trim()
val s = data.split("\r\n\r\n")
s.size

enum class Borders{ U, D, L, R }

data class Tile(val title: Int, val data: List<String>) {
    override fun toString(): String =
        "Tile $title:\n" + data.joinToString("\n")
    val connections = mutableMapOf<Borders,Int>()
    val borders: List<Pair<Borders,String>> = listOf(
        Borders.U to data.first(),                      // None
        Borders.D to data.last(),                       // HF.VF
        Borders.L to data.joinToString("")      // RR = Tr
            { it.first().toString() },
        Borders.R to data.joinToString("")      // 3*RR = RR.HF.VF
            { it.last().toString() },)
    val bordersTransformed: List<Pair<Borders,String>> =
        borders.map { it.first to it.second.reversed() }
    val allBorders = borders + bordersTransformed
    val edges = borders.map { it.second }
    val allEdges = allBorders.map { it.second }
}

fun parse(s: String): Tile {
    val titleR = """Tile (\d+):""".toRegex()
    val lines = s.lines()
    val title = titleR.find(lines.first())!!.destructured.component1()
    return Tile(title.toInt(),lines.drop(1))
}

val ims = s.map {parse(it)}
val tiles = ims.map { it.title to it }.toMap()
val t = Tile(0, listOf("12","34"))
t.borders
t.edges
t.allEdges
ims.map { it.title }
val borders = ims.map { it.title to it.borders.map { it.second } }
borders

fun adjacency(allBorders: List<Pair<Int,List<String>>>): Map<Int, Map<Int, List<Pair<Int,Int>>>> =
    allBorders.map{ (tileId, borders) ->
        tileId to borders.withIndex().map { (borderId, border) ->
            borderId to allBorders.filter { (otherTileId, _) -> otherTileId != tileId }
                .flatMap { (otherTileId, otherOrientation) ->
                    (otherOrientation + otherOrientation.map { it.reversed() })
                        .withIndex()
                        .filter { (_, otherBorder) -> otherBorder == border}
                        .map{ (otherBorderId, _) -> otherBorderId }
                        .map{ otherTileId to it }}
        }.toMap()
    }.toMap()
val adjacency = adjacency(borders)
val corners = adjacency.filter{ entry ->  entry.value.values.count{it.isNotEmpty()} == 2}.toList()
corners
corners.map{ pair -> pair.first.toLong()}.reduce(Long::times)
ims.flatMap { tile -> tile.allEdges
    .map { border -> tile.title to border } }
    .groupBy({it.second},{it.first}).filterValues { it.size >= 2 }
    .values.flatten()
    .groupingBy { it }
    .eachCount().filterValues { it == 4 }.keys
    .map { it.toLong() }.reduce(Long::times)
val allConnections = ims.flatMap { tile -> tile.allBorders
    .map { (id, border) -> (tile.title to id) to border } }
    .groupBy({it.second},{it.first}).filterValues { it.size >= 2 }
    .values.groupBy ({ it.first().first },{ it.first().second to it.last()})
allConnections
val connected = mutableSetOf<Int>()
allConnections.forEach{ (id,lst) ->
//    val cons = lst.map { (border, conn) -> border to conn.first }
//    connected.add(id)
//    //println(cons)
//    tiles[id]!!.connections.putAll(cons)
//    val oppositeCons = lst.map { (_, conn) -> conn }
    //println(oppositeCons)
    lst.map { it.second }.forEach { (oppositeId, oppositeBorder) ->
        tiles[oppositeId]!!.connections[oppositeBorder] = id
    }
}
tiles.forEach { println("${it.key} => ${it.value.connections}") }