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
    .map { it.split(" bags contain ")
        .let { it.first() to it.last() }}
    .toMap()
s
s.filter{ it.value.contains("shiny gold bag")}.keys
fun findAncestorBag(bags: Map<String,String>, bag: String): Set<String> =
    bags.filter { it.value.contains(bag)}.keys
val r = findAncestorBag(s,"shiny gold bag")
r
var curBags = listOf("shiny gold bag")
val result = mutableSetOf<String>()
while (curBags.isNotEmpty()){
    curBags = curBags.map { findAncestorBag(s,it) }.flatten()
    result.addAll(curBags)
}
result