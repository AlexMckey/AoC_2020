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

val strs = "1-3 a: abcde\n1-3 b: cdefg\n2-9 c: ccccccccc"

strs.split("\n").count(checkCountPolicy)
strs.split("\n").count(checkPosPolicy)

val ss = strs.split("\n")
val Array(policy, password) = ss(0).split(": ")
val Array(ints, ch) = policy.split(" ")
val chInPos = ints.split("-")
  .map(_.toInt)
  .map(i => password.lift(i-1))
  .map(_.contains(ch.head)).count(_ == true)