package day19.grammar

import AoCLib.SomeDay
import AoCLib.splitByBlankLines

object Day19_other:SomeDay(2020,19) {
    private sealed class Case {
        data class Lit(val ch: String) : Case()
        data class Pipe(val rules: List<List<Int>>) : Case()
    }

    private fun parse(s: String): Pair<Int, Case> {
        val ruleR = """(\d+): (.*)""".toRegex()
        val chR = """"(.)"""".toRegex()
        val (idStr, ruleStr) = ruleR.find(s)!!.destructured
        return idStr.toInt() to
                if (chR.matches(ruleStr)) Case.Lit(chR.find(ruleStr)!!.destructured.component1())
                else Case.Pipe(ruleStr.split(" | ").map { it.split(' ').map { it.toInt() } })
    }

    private fun build(rules: Map<Int, Case>, id: Int = 0, length: Int = 0, max: Int = Int.MAX_VALUE): String =
        if (length >= max) ""
        else when (val case = rules[id]!!){
            is Case.Lit -> case.ch
            is Case.Pipe -> {
                val cases = case.rules.map { r -> r.map { s -> build(rules, s, length + r.size, max) }
                    .joinToString("")}.joinToString("|")
                "($cases)"
            }
        }

    override fun first(data: String): Any? {
        val parts = data.splitByBlankLines()
        val rules = parts.first().lines().map(Day19_other::parse).toMap()
        val messages = parts.last().lines()
        val check = build(rules).toRegex()
        return messages.count { check.matches(it) }
    } // 187 Time: 62ms

    override fun second(data: String): Any? {
        val parts = data.splitByBlankLines()
        val rules = parts.first().lines().map(Day19_other::parse).toMap()
        val messages = parts.last().lines()
        val maxLength = messages.maxOfOrNull { it.length }!!
        val fixedRules = ("8: 42 | 42 8\n" +
                    "11: 42 31 | 42 11 31")
            .lines().map(Day19_other::parse).toMap()
        val newRules = rules + fixedRules
        val check = build(newRules, max = maxLength).toRegex()
        return messages.count { check.matches(it) }
    } // 392 Time: 127ms
}

fun main() = SomeDay.mainify(Day19_other)