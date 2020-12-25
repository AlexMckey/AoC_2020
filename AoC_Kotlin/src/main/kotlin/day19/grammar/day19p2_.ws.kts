import day19.grammar.Day19p1_better

sealed class Rule {
    data class Ch(val ch: Char) : Rule()
    data class Ref(val i: Int) : Rule()
    data class Cons(val left: Rule, val right: Rule) : Rule()
    data class Alts(val left: Rule, val right: Rule) : Rule()
}

val s = "1: 2 3 | 3 4 2"
val ruleR = """(\d+): (.*)""".toRegex()
val chR = """"(.)"""".toRegex()
val (idStr, ruleStr) = ruleR.find(s)!!.destructured
idStr
ruleStr
val allRuleR = """^(\d+): (?:(?:"(\w)")|((?:\d+\s?)+)|(?:((?:\d+\s?)+)\s\|\s((?:\d+\s?)+)))${'$'}""".toRegex()
val (id, ch, sr, ar1, ar2) = allRuleR.find(s)!!.destructured
id
ch
sr
ar1
ar2
val ar2i = ar2.split(' ').map { Rule.Ref(it.toInt()) }
    .reduceRight { s: Rule.Ref, acc: Rule -> Rule.Cons(s, acc) }
ar2i
//val sR = """(?<r11>(?<r42>abc)(?<r31>bb)|(?&r42)(?&r11)(?&r31))""".toRegex()