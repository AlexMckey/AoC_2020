package InputLib

import java.io.File

val InputDir = """d:\YandexDisk\DevsExercises\AdventOfCode\2020\PuzzleInput\"""

object DayInput {
    fun inputInts(filename: String) = File(InputDir+filename)
        .bufferedReader().lines().mapToInt(String::toInt)
}