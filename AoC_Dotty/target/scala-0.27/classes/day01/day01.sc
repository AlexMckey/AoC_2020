import scala.collection.mutable

val strs = "1721\n979\n366\n299\n675\n1456"
val ints = strs.split("\n").map(_.toInt).toList
def twoSum(nums: List[Int], target: Int): List[Int] = {
  val diffs = mutable.HashSet.empty[Int]
  nums.foreach(v => if (diffs.contains(v)) return List(v, target - v) else diffs.addOne(target - v))
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

val res1 = twoSum(ints, 2020).reduce(_*_)

val res2 = threeSum(ints, 2020).reduce(_*_)