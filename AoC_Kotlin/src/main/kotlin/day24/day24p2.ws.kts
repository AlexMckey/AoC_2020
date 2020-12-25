import AoCLib.Point

val r = """e|se|sw|w|nw|ne""".toRegex()
val s = "sesenwnenenewseeswwswswwnenewsewsw\n" +
        "neeenesenwnwwswnenewnwwsewnenwseswesw\n" +
        "seswneswswsenwwnwse\n" +
        "nwnwneseeswswnenewneswwnewseswneseene\n" +
        "swweswneswnenwsewnwneneseenw\n" +
        "eesenwseswswnenwswnwnwsewwnwsene\n" +
        "sewnenenenesenwsewnenwwwse\n" +
        "wenwwweseeeweswwwnwwe\n" +
        "wsweesenenewnwwnwsenewsenwwsesesenwne\n" +
        "neeswseenwwswnwswswnw\n" +
        "nenwswwsewswnenenewsenwsenwnesesenew\n" +
        "enewnwewneswsewnwswenweswnenwsenwsw\n" +
        "sweneswneswneneenwnewenewwneswswnese\n" +
        "swwesenesewenwneswnwwneseswwne\n" +
        "enesenwswwswneneswsenwnewswseenwsese\n" +
        "wnwnesenesenenwwnenwsewesewsesesew\n" +
        "nenewswnwewswnenesenwnesewesw\n" +
        "eneswnwswnwsenenwnwnwwseeswneewsenese\n" +
        "neswnwewnwnwseenwseesewsenwsweewe\n" +
        "wseweeenwnesenwwwswnew"
val s1 = "wsee\n" +
        "nesesw"
val ls = s.lines()
data class Cube(val x: Int = 0, val y: Int = 0, val z: Int = 0) {
    override fun toString(): String = "[x:$x,y:$y,z:$z]"
    operator fun plus(other: Cube) = Cube(other.x + x, other.y + y, other.z + z)
    fun near(): List<Cube> = Dir.values().map { it.dirToCube() }.map { this + it }
}
enum class Dir {E, SE, SW, W, NW, NE;
    fun dirToCube(): Cube = when (this) {
        Dir.E -> Cube(1, -1, 0)
        Dir.W -> Cube(-1, 1, 0)
        Dir.NE -> Cube(1, 0, -1)
        Dir.SW -> Cube(-1, 0, 1)
        Dir.NW -> Cube(0, 1, -1)
        Dir.SE -> Cube(0, -1, 1)
    }
}

ls[0]
val tilesCube = ls.map { r.findAll(it).map {
    Dir.valueOf(it.value.toUpperCase()).dirToCube()}
    .reduce(Cube::plus)
}
tilesCube
tilesCube.size
val cubeMap = tilesCube.groupingBy { it }.eachCount()
    .mapValues { it.value }
    .toMutableMap().filterValues { it % 2 == 1 }.withDefault { 0 }
cubeMap
cubeMap.size
cubeMap.count { it.value  % 2 == 1 }

val toBeBlack = cubeMap.keys.flatMap { it.near() }
    .minus(cubeMap.keys)
    .groupingBy { it }.eachCount().filterValues { it == 2 }
toBeBlack.keys
toBeBlack.size
val toBeWhite = cubeMap.filterNot { it.key.near().intersect(cubeMap.keys).count() in 1..2}
toBeWhite.keys
toBeWhite.size

val newBlack = cubeMap.keys + toBeBlack.keys - toBeWhite.keys
newBlack.size