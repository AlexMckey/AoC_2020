package day17

import AoCLib.Nears
import AoCLib.Point3D
import AoCLib.Point4D
import AoCLib.SomeDay

object Day17:SomeDay(2020,17) {
    private fun <T: Nears<T>>next(cube: Set<T>): Set<T> =
        cube.filter { it.near()
            .intersect(cube)
            .size in 2..3 }
            .union(cube
                .flatMap { it.near() }
                .groupingBy { it }.eachCount()
                .filter { it.value == 3 }
                .map { it.key })
    override fun first(data: String): Any? {
        var cube = data.lines()
            .flatMapIndexed{ y, l -> l
                .mapIndexed { x, ch ->
                    if (ch == '#') Point3D(x,y,0)
                    else null
                } }.filterNotNull().toSet()
        repeat(6) {cube = next(cube) }
        return cube.size
    } // 317 Time: 52ms

    override fun second(data: String): Any? {
        var hiperCube = data.lines()
            .flatMapIndexed{ y, l -> l
                .mapIndexed { x, ch ->
                    if (ch == '#') Point4D(x,y,0,0)
                    else null
                } }.filterNotNull().toSet()
        repeat(6) {hiperCube = next(hiperCube) }
        return hiperCube.size
    } // 1692 Time: 183ms
}

fun main() = SomeDay.mainify(Day17)