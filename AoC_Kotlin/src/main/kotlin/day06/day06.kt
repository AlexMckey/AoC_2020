package day06

import AoCLib.splitByBlankLines
import AoCLib.SomeDay

object Day06: SomeDay(2020,6) {
    override fun first(data: String): Any? {
        return data.splitByBlankLines()
            .sumBy {it.replace("\n", "")
                .toSet().size
        }
    } // 6930 Time: 35ms

    override fun second(data: String): Any? {
        return data.splitByBlankLines()
            .sumBy { it.split('\n')
                .map(String::toSet)
                .reduce(Set<Char>::intersect)
                .size
        }
    } // 3585 Time: 9ms
}

fun main() = SomeDay.mainify(Day06)