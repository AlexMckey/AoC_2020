package day07

import AoCLib.InputTransform.toStrs
import AoCLib.SomeDay

object Day07: SomeDay(2020,7) {
    private fun findAncestorBag(bags: Map<String,String>, bag: String): Set<String> =
        bags.filter { it.value.contains(bag)}.keys

    private fun makeTree(rules: Map<String, Map<String,Int>>, bagColor: String, cnt: Int = 1): BagTree =
        BagTree(rules[bagColor]?.map { makeTree(rules, it.key, it.value) }, cnt)
    class BagTree(private val children: List<BagTree>?, private val cnt: Int = 1) {
        val innerBagCount: Int
            get() = cnt * (children?.fold(1) { acc, bagTree -> acc + bagTree.innerBagCount } ?: 0)
    }

    override fun first(data: String): Any? {
        val bags = data.toStrs()
            .map { it.split(" bags contain ")
                .let { it.first() to it.last() }}
            .toMap()
        var curBags = listOf("shiny gold bag")
        val result = mutableSetOf<String>()
        while (curBags.isNotEmpty()){
            curBags = curBags.flatMap { findAncestorBag(bags,it) }
            result.addAll(curBags)
        }
        return result.size
    } // 332 Time: 61ms

    override fun second(data: String): Any? {
        val r1 = """(\w+ \w+) bags? contain (.+)\.\n""".toRegex()
        val r2 = """(?:(\d+) )?(\w+ \w+) bags?(?:, )?""".toRegex()
        val rules = r1.findAll(data).map { b ->
            b.groupValues[1] to r2.findAll(b.groupValues[2])
                .map {
                    val cntStr = it.groupValues[1]
                    it.groupValues[2] to if (cntStr == "") 0 else cntStr.toInt() }
                .toMap()
        }.toMap()
        return makeTree(rules,"shiny gold").innerBagCount-1
    } // 10875 Time: 22ms
}

fun main() = SomeDay.mainify(Day07)