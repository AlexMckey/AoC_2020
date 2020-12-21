package day19

import AoCLib.SomeDay
import AoCLib.splitByBlankLines

object Day19:SomeDay(2020,19) {
    private sealed class Rule {
        data class Ch(val ch: String) : Rule()
        data class Ref(val key: Int) : Rule()
    }

    private fun parseRule(s: String): Pair<Int, List<List<Rule>>> {
        val ruleR = """(\d+): (.*)""".toRegex()
        val chR = """"(.)"""".toRegex()
        val (idStr, ruleStr) = ruleR.find(s)!!.destructured
        return idStr.toInt() to ruleStr.split(" | ")
            .map { it.split(' ')
                .map { if (chR.matches(it))
                        Rule.Ch(chR.find(it)!!.destructured.component1())
                    else Rule.Ref(it.toInt())
            }
        }
    }

    private fun toRegexString(m: Map<Int, List<List<Rule>>>, key: Int): String =
        m[key]!!
        .joinToString("|","(?:",")") {
            it.joinToString("") {
                when (it) {
                    is Rule.Ch -> it.ch
                    is Rule.Ref -> toRegexString(m,it.key)
                }
            }
        }

    private fun convertToRules(s: String): Map<Int,List<List<Rule>>> =
        s.lines().map { parseRule(it) }.toMap()

    override fun first(data: String): Any? {
        val parts = data.splitByBlankLines()
        val rules = convertToRules(parts.first())
        val check = toRegexString(rules,0).toRegex()
        return parts.last().lines().count(check::matches)
    } // 187 Time: 70ms

    override fun second(data: String): Any? {
        val parts = data.splitByBlankLines()
        val rules = convertToRules(parts.first())
        // hack
        val pattern31 = toRegexString(rules,31)
        val pattern42 = toRegexString(rules,42)
        val messages = parts.last().lines()
        val n = messages.maxOfOrNull { it.length } ?: 0
        val check = (1..(n - 1) / 2).joinToString("|") { i ->
            "(?:$pattern42){${i + 1},}(?:$pattern31){$i}"
        }.toRegex()
        return messages.count(check::matches)
    } // 392 Time: 77ms
}

fun main() = SomeDay.mainify(Day19)