import scala.collection.mutable.HashSet

object day01 {

  val expenses = myLib.inputInts("input01.txt")
  
  def twoSum(nums: List[Int], target: Int): List[Int] = {
    val diffs = HashSet.empty[Int]
    nums.foreach(v =>
      if (diffs.contains(v))
        return List(v, target - v)
      else diffs.addOne(target - v))
    throw IllegalArgumentException(s"Not contains pair of num with sum of $target")
  }
  def threeSum(nums: List[Int], target: Int): List[Int] =
  {
    val ia = nums.sorted
    val len = ia.size
    for (i <- 0 until len - 3) {
      var l = i + 1
      var r = len - 1
      while (l < r) {
        val list = List(ia(i), ia(l), ia(r))
        list.sum match {
          case x if x == target => return list
          case x if x < target => l += 1
          case _ => r -= 1
        }
      }
    }
    throw IllegalArgumentException("No solution")
  }
  
  def main(args: Array[String]): Unit = {
    val res1 = twoSum(expenses, 2020).reduce(_*_)
    Console.println(res1) // 388075
    val res2 = threeSum(expenses, 2020).reduce(_*_)
    Console.println(res2) // 293450526
  }
}
