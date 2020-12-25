data class Bag(val color: String, val cnt: Int = 1) {
    companion object {
        val EmptyBag = Bag("empty", 0)
    }
    override fun toString(): String = "$color:$cnt"
}
class BagTree(val bag: Bag) {
    val children: MutableList<BagTree> = mutableListOf()
    val innerBagCount: Int
        get() = bag.cnt * children.fold(1) { acc, bagTree -> acc + bagTree.innerBagCount }
    override fun toString(): String {
        var s = "${bag}"
        if (children.isNotEmpty()) {
            s += " {" + children.map { it.toString() } + " }"
        }
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
val r1 = """(\w+ \w+) bags? contain (.+)\.\n""".toRegex()
val r2 = """(?:(\d+) )?(\w+ \w+) bags?(?:, )?""".toRegex()
val s1 = s.split('\n').first()
s1
val (color, innerBag) = """(\w+ \w+) bags? contain (.+)\.""".toRegex().find(s1)!!.destructured
color
innerBag
val rr = r1.findAll(s).map { b ->
    b.groupValues[1] to r2.findAll(b.groupValues[2])
        .map {
            if (it.groupValues[1].isNullOrEmpty())
                Bag.EmptyBag
            else Bag(it.groupValues[2],it.groupValues[1].toInt()) }
        .toList()
}.toMap()
rr
fun makeTree(rules: Map<String,List<Bag>>, bag: Bag): BagTree {
    val res = BagTree(bag)
    if (bag == Bag.EmptyBag) return res
    res.children.addAll(rules[bag.color]?.map { makeTree(rules, it) } ?: emptyList())
    return res
}
val bt = makeTree(rr,Bag("shiny gold"))
bt
bt.innerBagCount-1