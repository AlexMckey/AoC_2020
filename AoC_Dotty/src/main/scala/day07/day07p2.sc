case class Bag(val color: String, val cnt: Int = 1) {
    override def toString(): String = s"$color:$cnt"
}
object Bag {
  val EmptyBag = Bag("empty", 0)
}

import scala.collection.mutable._

class BagTree(val bag: Bag) {
    val children: ListBuffer[BagTree] = ListBuffer.empty
    def innerBagCount: Int = 1 + children.map(_.innerBagCount).sum
  override def toString(): String = {
    var s = s"$bag"
    if (!children.isEmpty)
      s += " {" + children.map(_.toString) + " }"
    return s
  }
}

val s = "light red bags contain 1 bright white bag, 2 muted yellow bags.\n" +
        "dark orange bags contain 3 bright white bags, 4 muted yellow bags.\n" +
        "bright white bags contain 1 shiny gold bag.\n" +
        "muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.\n" +
        "shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.\n" +
        "dark olive bags contain 3 faded blue bags, 4 dotted black bags.\n" +
        "vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.\n" +
        "faded blue bags contain no other bags.\n" +
        "dotted black bags contain no other bags."
val r1 = """(\w+ \w+) bags? contain (.+)\.\n""".r
val r1_ = """(?<color>\w+ \w+) bags? contain (?<innerbags>.+)\.""".r
val r2 = """(?:(\d+) )?(\w+ \w+) bags?(?:, )?""".r
val s1 = s.split('\n').head
val r = s1 match {
  case r1_(color,inner) => color -> inner
}
r._2 match {
  case r2(cnt,bag) => s"$bag:$cnt"
}
val res = r1_.findAllIn(s1)
res.group(1)
res.group(2)
val rr = r1_.findAllMatchIn(s)
  .map(om => om.group("color") -> r2
    .findAllMatchIn(om.group("innerbags"))
    .map { im =>
      val ib = im.group(2)
      val cntStr = im.group(1)
      val cnt = if (cntStr == null) 0 else cntStr.toInt
      Bag(ib,cnt)
    }.toList)
  .toMap

def makeTree(rules: Map[String, List[Bag]], bag: Bag): BagTree = {
    val res = BagTree(bag)
    if (bag == Bag.EmptyBag) return res
    res.children.addAll(if (rules.contains(bag.color)) rules(bag.color).map(makeTree(rules,_)) else List.empty)
    return res
}
val bt = makeTree(rr,Bag("shiny gold"))
bt
bt.innerBagCount-1