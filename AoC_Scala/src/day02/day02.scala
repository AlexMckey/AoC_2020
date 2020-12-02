package day02

object day02 {

  def checkCountPolicy(s: String): Boolean = {
    val Array(policy, password) = s.split(": ")
    val Array(ints, ch) = policy.split(" ")
    val Array(i1, i2) = ints.split("-").map(_.toInt)
    val charsCount = password.groupBy(c => c)
      .map{ case (ch,str) => ch -> str.length }
    charsCount.contains(ch.head) &&
      charsCount(ch.head) >= i1 &&
      charsCount(ch.head) <= i2
  }

  def checkPosPolicy(s: String): Boolean = {
    val Array(policy, password) = s.split(": ")
    val Array(ints, ch) = policy.split(" ")
    val chInPos = ints
      .split("-")
      .map(_.toInt)
      .map(i => password.lift(i-1))
      .map(_.contains(ch.head))
      .count(_ == true)
    chInPos == 1
  }

  val strs: Seq[String] = AoC_Lib.inputStrs("input02.txt")

  def main(args: Array[String]): Unit = {
    val res1 = strs.count(checkCountPolicy)
    println(res1) // 445
    val res2 = strs.count(checkPosPolicy)
    println(res2) // 491
  }
}