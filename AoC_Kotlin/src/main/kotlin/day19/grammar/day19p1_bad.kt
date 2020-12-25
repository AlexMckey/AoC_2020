package day19.grammar

import AoCLib.SomeDay
import AoCLib.headAndTail
import AoCLib.splitByBlankLines
import kotlin.math.max

object Day19_Bad:SomeDay(2020,19) {
    open class OldRule {
        open fun replace(m: Map<Int, OldRule>): OldRule = this
        open fun isFlat():Boolean = true
        open fun size(): Int = 0
        open fun flatten(): List<String> = listOf("")
    }
    data class Lit(val ch: Char): OldRule() {
        override fun size(): Int = 1
        override fun flatten(): List<String> = listOf(ch.toString())
    }
    data class Id(val id: Int): OldRule() {
        override fun replace(m: Map<Int, OldRule>): OldRule {
            var res  = m.getOrDefault(id, OldRule())
            while (!res.isFlat())
                res = res.replace(m)
            return res
        }
        override fun isFlat(): Boolean = false
    }
    data class Sr(val lst: List<OldRule>): OldRule() {
        override fun replace(m: Map<Int, OldRule>): OldRule =
            Sr(lst.map { it.replace(m) })
        override fun isFlat(): Boolean = lst.all { it.isFlat() }
        override fun size(): Int = lst.map { it.size() }.sum()
        override fun flatten(): List<String> {
            tailrec fun helper(tail: List<OldRule>, acc: List<String>): List<String> {
                if (tail.isEmpty()) return acc
                val (h,t) = tail.headAndTail()
                return helper(t, h.flatten().flatMap { st -> acc.map { it + st } })
            }
            return helper(lst, listOf(""))
        }
    }
    data class Ar(val r1: OldRule, val r2: OldRule): OldRule(){
        override fun replace(m: Map<Int, OldRule>): OldRule =
            Ar(r1.replace(m), r2.replace(m))
        override fun isFlat(): Boolean =
            r1.isFlat() && r2.isFlat()
        override fun size(): Int = max(r1.size(),r2.size())
        override fun flatten(): List<String> = r1.flatten() + r2.flatten()
    }

    private val sr = """^(\d+): (?:(?:"(\w)")|((?:\d+\s?)+)|(?:((?:\d+\s?)+)\s\|\s((?:\d+\s?)+)))${'$'}""".toRegex()
    private fun convToRules(s: String): Map<Int, OldRule> =
        s.lines().flatMap { sr.findAll(it).map {
            val (id, ch, sr, ar1, ar2) = it.destructured
            id.toInt() to when {
                ch.isNotEmpty() -> Lit(ch.first())
                sr.isNotEmpty() -> Sr(sr.split(' ').map { Id(it.toInt()) })
                ar1.isNotEmpty() -> Ar(
                    Sr(ar1.split(' ').map { Id(it.toInt()) }),
                    Sr(ar2.split(' ').map { Id(it.toInt()) })
                )
                else -> OldRule()
            }
        }}.toMap()

    override fun first(data: String): Any? {
        val parts = data.splitByBlankLines()
        val rules = convToRules(parts.first())
        val resRule = rules[0]!!.replace(rules)
        val resStrs = resRule.flatten()
        return parts.last().lines().count{ resStrs.contains(it) }
    } // 187 Time: 2951ms
}

fun main() = SomeDay.mainify(Day19_Bad)