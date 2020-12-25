package day19.grammar

import AoCLib.SomeDay
import AoCLib.splitByBlankLines

object Day19p1_better:SomeDay(2020,19) {
    private sealed class Rule {
        data class Ch(val ch: Char) : Rule()
        data class Ref(val i: Int) : Rule()
        data class Cons(val left: Rule, val right: Rule) : Rule()
        data class Alts(val left: Rule, val right: Rule) : Rule()
    }

    private fun rulesToChecks(rules: Map<Int, Rule>): (String) -> Boolean {
        val memoRules = mutableMapOf<Int,String>()
        fun helper(rule: Rule): String = when (rule) {
            is Rule.Ch -> rule.ch.toString()
            is Rule.Ref -> {
                if (memoRules.containsKey(rule.i)) memoRules[rule.i]!!
                else {
                    val add = helper(rules[rule.i]!!)
                    memoRules[rule.i] = add
                    add
                }
            }
            is Rule.Cons -> helper(rule.left) + helper(rule.right)
            is Rule.Alts -> "(${helper(rule.left)}|${helper(rule.right)})"
        }
        val r = helper(Rule.Ref(0)).toRegex()
        return {s: String -> r.matches(s)}
    }


    private fun convertToRules(s: String): Map<Int, Rule> {
        val ruleR = """(\d+): (.*)""".toRegex()
        val chR = """"(.)"""".toRegex()
        val allRuleR = """^(\d+): (?:(?:"(\w)")|((?:\d+\s?)+)|(?:((?:\d+\s?)+)\s\|\s((?:\d+\s?)+)))${'$'}""".toRegex()
        return s.lines().flatMap { allRuleR.findAll(it).map {
            val (id, ch, sr, ar1, ar2) = it.destructured
            id.toInt() to when {
                ch.isNotEmpty() -> Rule.Ch(ch.first())
                sr.isNotEmpty() -> sr.split(' ')
                    .map { Rule.Ref(it.toInt()) }.reduceRight{ ref, acc: Rule -> Rule.Cons(ref, acc) }
                ar1.isNotEmpty() -> Rule.Alts(
                    ar1.split(' ')
                        .map { Rule.Ref(it.toInt()) }
                        .reduceRight { ref, acc: Rule -> Rule.Cons(ref, acc) },
                    ar2.split(' ')
                        .map { Rule.Ref(it.toInt()) }
                        .reduceRight { ref, acc: Rule -> Rule.Cons(ref, acc) })
                else -> Rule.Ch('0')
            }
        }}.toMap()
    }

    override fun first(data: String): Any? {
        val parts = data.splitByBlankLines()
        val rules = convertToRules(parts.first())
        val checks = rulesToChecks(rules)
        return parts.last().lines().count(checks)
    } // 187 Time: 70ms

    override fun second(data: String): Any? {
        val parts = data.splitByBlankLines()
        val rules = convertToRules(parts.first())
        val messages = parts.last().lines()
        val maxLenght = messages.maxOfOrNull { it.length }!!
        val fixedRules = convertToRules("8: 42 | 42 8\n" +
                "11: 42 31 | 42 11 31")
        val newRules = rules + fixedRules
        val checks = rulesToChecks(newRules)
        return messages.count(checks)
    } // error - recursion
}

fun main() = SomeDay.mainify(Day19p1_better)