package object myLib {
  val DataDir = """d:\YandexDisk\DevsExercises\AdventOfCode\2020\PuzzleInput\"""
  def input(filename: String): Iterator[String] = {
    scala.io.Source.fromFile(DataDir+filename).getLines
  }
  def inputInts(filename: String): List[Int] = input(filename).map(_.toInt).toList
}
