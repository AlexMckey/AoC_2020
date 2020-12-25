import AoCLib.MapGrid
import AoCLib.Point
import AoCLib.toDirPoints
import AoCLib.toMapGrid

val s = "L.LL.LL.LL\n" +
        "LLLLLLL.LL\n" +
        "L.L.L..L..\n" +
        "LLLL.LL.LL\n" +
        "L.LL.LL.LL\n" +
        "L.LLLLL.LL\n" +
        "..L.L.....\n" +
        "LLLLLLLLLL\n" +
        "L.LLLLLL.L\n" +
        "L.LLLLL.LL"
val s1 = ".......#.\n" +
        "...#.....\n" +
        ".#.......\n" +
        ".........\n" +
        "..#L....#\n" +
        "....#....\n" +
        ".........\n" +
        "#........\n" +
        "...#....."
val s2 = ".............\n" +
        ".L.L.#.#.#.#.\n" +
        "............."
val s3 = ".##.##.\n" +
        "#.#.#.#\n" +
        "##...##\n" +
        "...L...\n" +
        "##...##\n" +
        "#.#.#.#\n" +
        ".##.##."
val seats = s3.lines()
    .toMapGrid()
fun occupied(grid: MapGrid<Char>) = grid.values.count { it == '#' }
fun countNearNeighbors(grid: MapGrid<Char>, p: Point, ch: Char) =
    p.near8().mapNotNull { grid[it] }.count { it == ch }
fun countVisibleNeighbors(grid: MapGrid<Char>, p: Point, ch: Char) =
    Point.Zero.near8().mapNotNull {
        p.toDirPoints(it).find { it in grid && grid[it] != '.' }
    }.count { grid[it] == ch }
occupied(seats)
val curSeat = Point.Zero + Point.toU
countNearNeighbors(seats,curSeat,'#')
val posL = seats.filterValues { it == 'L' }.keys.first()
posL
posL.toDirPoints(Point.toD).take(10).toList().find { it in seats && seats[it] != '.' }
posL.toDirPoints(Point.toUL).take(10).toList().find { it in seats && seats[it] != '.' }
//countVisibleNeighbors(seats,posL,'#')