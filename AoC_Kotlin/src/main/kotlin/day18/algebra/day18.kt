package day18.algebra

import AoCLib.SomeDay
import AoCLib.toStrs
import java.util.*

object Day18:SomeDay(2020,18) {
    override val title = "Operation Order"

    private val rs = """[()\d+*]""".toRegex()

    private fun tokenize(s: String, opPrecedences: Map<String,Int>): MutableList<String> {
        val tokens = mutableListOf<String>()
        val ops = Stack<String>()
        rs.findAll(s).forEach {
            when (val op = it.value) {
                "(" -> ops.push("(")
                ")" -> {
                    while (ops.peek() != "(")
                        tokens.add(ops.pop())
                    ops.pop()
                }
                "*","+" -> {
                    while (ops.isNotEmpty()
                            && ops.peek() != "("
                            && opPrecedences.getOrDefault(ops.peek(),0)
                            >= opPrecedences.getOrDefault(op,0))
                        tokens.add(ops.pop())
                    ops.push(op)
                }
                else -> tokens.add(op)
            }
        }
        tokens.addAll(ops.reversed())
        return tokens
    }
    private fun evaluate(tokens: MutableList<String>): Long {
        val acc = Stack<Long>()
        tokens.forEach{
            acc.push(when (it) {
                "+" -> acc.pop() + acc.pop()
                "*" -> acc.pop() * acc.pop()
                else -> it.toLong()
            })
        }
        return acc.last()
    }

    private fun calc(s: String, opPrecedences:Map<String,Int> = mapOf("+" to 0, "*" to 0)): Long =
        evaluate(tokenize(s,opPrecedences))

    override fun first(data: String): Any? =
        data.toStrs().map { calc(it) }.sum()
    // 5783053349377 Time: 50ms

    override fun second(data: String): Any? =
        data.toStrs().map { calc(it, mapOf("*" to 0, "+" to 1)) }.sum()
    // 74821486966872 Time: 9ms
}

fun main() = SomeDay.mainify(Day18)