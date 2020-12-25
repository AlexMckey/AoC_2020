val area = "..##.......\n" +
        "#...#...#..\n" +
        ".#....#..#.\n" +
        "..#.#...#.#\n" +
        ".#...##..#.\n" +
        "..#.##.....\n" +
        ".#.#.#....#\n" +
        ".#........#\n" +
        "#.##...#...\n" +
        "#...##....#\n" +
        ".#..#...#.#"
data class Point(val x: Int, val y: Int) {
    override fun toString(): String = "[x:$x,y:$y]"
    operator fun plus(other: Point) = Point(other.x + x, other.y + y)
    operator fun plus(other: Pair<Int, Int>) = Point(other.first + x, other.second + y)
}
var cur = Point(0,0)
val angle = 3 to 1
val next = cur + angle
next
val grid = area.split("\n").toTypedArray()
val height = grid.size
val weight = grid.first().length
height
var treeCount = 0
while (cur.y < height)
{
    print("$cur = ")
    println("${grid[cur.y][cur.x % weight]}")
    if (grid[cur.y][cur.x % weight] == '#') treeCount++
    cur += angle
    //if (cur.x >= weight-1) cur = Point(weight - cur.x + 1, cur.y)
    println("Next pos: $cur")
}
treeCount
val seqs = generateSequence(Point(0,0)) { Point((it.x + angle.first) % weight, it.y + angle.second) }
val idxs = seqs.takeWhile { it.y < height }.toList()
idxs
idxs.count { grid[it.y][it.x] == '#' }