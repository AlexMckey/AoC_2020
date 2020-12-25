package day16.tickets

import AoCLib.SomeDay
import AoCLib.splitByBlankLines

object Day16:SomeDay(2020,16){
    override val title = "Ticket Translation"

    override fun first(data: String): Any? {
        val threePart = data.splitByBlankLines()
        val rulesStr = threePart.first()
        val simpleRuleR = """(\d+)-(\d+)""".toRegex(RegexOption.MULTILINE)
        val ur = simpleRuleR.findAll(rulesStr).map {
            it.groups[1]!!.value.toInt()..it.groups[2]!!.value.toInt() }
            .fold(emptySet<Int>()){ acc, intRange -> acc.union(intRange) }
            .toList()
        val nums = threePart.last().split('\n').drop(1)
            .flatMap { it.split(',').map { it.toInt() } }
        return nums.filter { !ur.contains(it) }.sum()
    } // 20048 Time: 47ms

    private tailrec fun associateFiledNames(multipleFields: Map<Int, Set<String>>, singleFields: Map<Int, Set<String>> = emptyMap()): List<Pair<Int,String>> {
        if (multipleFields.isEmpty()) return singleFields.map { it.key to it.value.first() }
        val singles = multipleFields.filter { it.value.size == 1 }
        val reductedFields = multipleFields.filter { !singles.keys.contains(it.key) }
            .mapValues { singles.values.fold(it.value){acc, set -> acc.minus(set) } }
        return associateFiledNames(reductedFields,singleFields+singles)
}

    override fun second(data: String): Any? {
        val threePart = data.splitByBlankLines()
        val rulesStr = threePart.first()
        val ruleR = """(?<field>(?:\w|\s)+): (?<fr1>\d+)-(?<fr2>\d+) or (?<sr1>\d+)-(?<sr2>\d+)\n?""".toRegex()
        val rules = ruleR.findAll(rulesStr)
            .map{
                val (name, r1, r2, r3, r4) = it.destructured
                name to {x: Int -> x in r1.toInt()..r2.toInt() || x in r3.toInt()..r4.toInt()}}
            .toList()
        val myTicket = threePart.drop(1).first().split('\n').last()
            .split(',')
            .map { it.toInt() }
        val tickets = threePart.last().split('\n').drop(1)
            .map { it.split(',')
                .map { it.toInt() } }
        val fields = tickets
            .flatMap { ticket -> ticket
                .mapIndexed { index, num -> index to rules
                    .filter { it.second(num) }
                    .map { it.first }.toSet() } }
            .filter { it.second.isNotEmpty() }
            .groupBy({ it.first },{it.second})
            .mapValues { it.value
                .reduce(Set<String>::intersect) }
        val fieldsName = associateFiledNames(fields)
        val departureIdxs = fieldsName
            .filter { it.second
                .contains("departure") }
            .map { it.first }
        return myTicket
            .slice(departureIdxs)
            .map { it.toLong() }
            .reduce(Long::times)
    } // 4810284647569 Time: 45ms
}

fun main() = SomeDay.mainify(Day16)