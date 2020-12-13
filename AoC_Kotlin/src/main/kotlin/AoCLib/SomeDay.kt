package AoCLib

import java.io.File
import kotlin.system.measureTimeMillis

/**
 * Abstract class representing solution for [day]s problem in specified [year].
 */
open class SomeDay(val year: Int, val day: Int) {
    private val inputDir = "d:\\YandexDisk\\DevsExercises\\AdventOfCode\\$year\\PuzzleInput\\"
    //private val inputDir = "C:\\Users\\MakievskyAV\\Desktop\\Devs\\AoC_2020\\PuzzleInput\\"
    private val filepath = inputDir + "input${day.toString().padStart(2,'0')}.txt"
    val data: String = File(filepath).bufferedReader().readText().trim()

    open fun first(data: String): Any? = null

    open fun second(data: String): Any? = null

    companion object {
        fun mainify(someday: SomeDay) {
            with(someday) {
                println("Year $year, day $day")
                measureTimeMillis {
                    println("First: ${first(data)?.toString() ?: "unsolved"}")
                }.run {
                    println("Time: ${this}ms")
                }
                measureTimeMillis {
                    println("Second: ${second(data)?.toString() ?: "unsolved"}")
                }.run {
                    println("Time: ${this}ms")
                }
            }
        }
    }
}