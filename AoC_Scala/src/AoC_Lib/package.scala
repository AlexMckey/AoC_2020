import scala.io.Source._

package object AoC_Lib {
  val DataDir = """d:\YandexDisk\DevsExercises\AdventOfCode\2020\PuzzleInput\"""
  def input(filename: String): Iterator[String] = fromFile(DataDir+filename).getLines
  def inputInts(filename: String): Seq[Int] = input(filename).map(_.toInt).toList
  def inputStrs(filename: String): Seq[String] = input(filename).toList
}