val strs = "1-3 a: abcde\n1-3 b: cdefg\n2-9 c: ccccccccc"
case class Policy(val ch: Char, val i1: Int, val i2: Int)

val policyPasswords = strs.lines().map(s => {
  val arr = s.split(": ")
  val policyArr = arr.head.split(" ")
  val policyInts = policyArr.head.split("-").map(_.toInt)
  Policy(policyArr.last.head,policyInts.head,policyInts.last)
} )