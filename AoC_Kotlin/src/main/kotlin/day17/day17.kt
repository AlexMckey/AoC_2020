package day17

import AoCLib.Nears
import AoCLib.Point3D
import AoCLib.Point4D
import AoCLib.SomeDay

object Day17:SomeDay(2020,17) {
    private fun <T: Nears<T>> next(cube: Set<T>): Set<T> =
        cube.flatMap { it.near() }
            .groupingBy { it }.eachCount()
            .filter { it.value == 3 || it.value == 2 && cube.contains(it.key) }
            .keys
    private fun <T: Nears<T>> toPocketDimensions(s: String, f: (x: Int, y: Int, z: Int, w: Int) -> T): Set<T> =
        s.lines()
            .flatMapIndexed{ y, l -> l
            .mapIndexed { x, ch ->
            if (ch == '#') f(x,y,0,0)
            else null
        } }.filterNotNull().toSet()

    override fun first(data: String): Any? {
        var cube = toPocketDimensions(data) { x,y,z,_ -> Point3D(x,y,z)}
        repeat(6) {cube = next(cube) }
        return cube.size
    } // 317 Time: 39ms

    override fun second(data: String): Any? {
        var hyperCube = toPocketDimensions(data) { x,y,z,w -> Point4D(x,y,z,w)}
        repeat(6) {hyperCube = next(hyperCube) }
        return hyperCube.size
    } // 1692 Time: 94ms
}

fun main() = SomeDay.mainify(Day17)