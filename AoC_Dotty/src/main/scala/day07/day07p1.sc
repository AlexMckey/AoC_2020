val istr = "light red bags contain 1 bright white bag, 2 muted yellow bags.\n" +
        "dark orange bags contain 3 bright white bags, 4 muted yellow bags.\n" +
        "bright white bags contain 1 shiny gold bag.\n" +
        "muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.\n" +
        "shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.\n" +
        "dark olive bags contain 3 faded blue bags, 4 dotted black bags.\n" +
        "vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.\n" +
        "faded blue bags contain no other bags.\n" +
        "dotted black bags contain no other bags."
val s = istr.split('\n')
    .map { s => val Array(color, inner) = s.split(" bags contain ")
          color -> inner }
    .toMap
s.filter{ _._2.contains("shiny gold bag")}.keys

def findAncestorBag(bags: Map[String,String], bag: String) =
  bags.filter(p => p._2.contains(bag)).keySet
val r = findAncestorBag(s,"shiny gold bag")

var curBags = List("shiny gold bag")
var result = scala.collection.mutable.Set.empty[String]

while (!curBags.isEmpty){
    curBags = curBags.flatMap(findAncestorBag(s,_))
    result.addAll(curBags)
}

result.size