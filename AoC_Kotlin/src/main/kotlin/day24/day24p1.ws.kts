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
    //operator fun plus(other: Triple<Int,Int,Int>) = Cube(other.first + x, other.second + y, z + other.third)
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
val cubeMap = mutableMapOf<Cube,Int>()
ls[0]
val tilesCube = ls.map { r.findAll(it).map {
    Dir.valueOf(it.value.toUpperCase()).dirToCube()}
    .reduce(Cube::plus)
}
tilesCube
tilesCube.size
tilesCube.forEach {
    cubeMap.merge(it,1,Int::plus)
}
cubeMap.count { it.value % 2 == 0 }
val cm = tilesCube.groupingBy { it }.eachCount()
cm
cm.size