package day06

import AoCLib.InputTransform.splitByBlankLines
import AoCLib.SomeDay

object Day06: SomeDay(2020,6) {
    override fun first(data: String): Any? {
        return data.splitByBlankLines()
            .map {it.replace("\n", "")
                .toSet().size
        }.sum()
    } // 6930 Time: 35ms

    override fun second(data: String): Any? {
        return data.splitByBlankLines()
            .map { it.split('\n')
                .map { it.toSet() }
                .reduce { acc, set -> acc.intersect(set) }
                .count()
        }.sum()
    } // 3585 Time: 9ms
}

fun main() = SomeDay.mainify(Day06)