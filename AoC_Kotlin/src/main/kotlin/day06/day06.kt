package day06

import InputLib.DayInput

fun main() {
    val s = DayInput.inputStr("input06.txt").dropLast(1)
    val gs = s.split("\n\n")
    val res1 = gs.map {
        it.replace("\n","")
            .toSet().size
    }.sum()
    println(res1) // 6930
    val res2 = gs.map {
        it.split('\n')
            .map { it.toSet() }
            .reduce { acc, set -> acc.intersect(set) }
            .count()
    }.sum()
    println(res2) // 3585
}