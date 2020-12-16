import AoCLib.splitByBlankLines

val s = "class: 1-3 or 5-7\n" +
        "row: 6-11 or 33-44\n" +
        "seat: 13-40 or 45-50\n" +
        "\n" +
        "your ticket:\n" +
        "7,1,14\n" +
        "\n" +
        "nearby tickets:\n" +
        "7,3,47\n" +
        "40,4,50\n" +
        "55,2,20\n" +
        "38,6,12"
val threePart = s.splitByBlankLines()
val rulesStr = threePart.first()
rulesStr
val ruleR = """(?<field>(?:\w|\s)+): (?<fr1>\d+)-(?<fr2>\d+) or (?<sr1>\d+)-(?<sr2>\d+)""".toRegex(RegexOption.MULTILINE)
val simpleRuleR = """(\d+)-(\d+)""".toRegex(RegexOption.MULTILINE)
val rs = simpleRuleR.findAll(rulesStr).map {
    //println(it.groupValues)}
    it.groups[1]!!.value.toInt()..it.groups[2]!!.value.toInt() }
    .toList()
rs
val ur = rs.fold(emptySet<Int>()){ acc, intRange -> acc.union(intRange) }
ur
val nums = threePart.last().split('\n').drop(1)
    .flatMap { it.split(',').map { it.toInt() } }
nums
val r1 = 1..3
val r2 = 5..7
val r3 = 6..11
val r4 = 33..44
val r5 = 13..40
val r6 = 45..50
val urs = r1.union(r2).union(r3).union(r4).union(r5).union(r6)
urs
nums.filter { !urs.contains(it) }.sum()
nums.filter { !ur.contains(it) }.sum()