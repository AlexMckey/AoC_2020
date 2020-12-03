package InputLib

import java.io.File
import kotlin.sequences.*
// Home WorkDir
//val InputDir = """d:\YandexDisk\DevsExercises\AdventOfCode\2020\PuzzleInput\"""
// Work WorkDir
val InputDir = """d:\DevsExercises\AdventOfCode\2020\PuzzleInput\"""

object DayInput {
    fun inputIntsArray(filename: String) = File(InputDir+filename)
        .bufferedReader().lines().mapToInt(String::toInt).toArray()
    fun inputStrsList(filename: String) = Sequence { File(InputDir+filename)
        .bufferedReader().lines().iterator() }.toList()
}