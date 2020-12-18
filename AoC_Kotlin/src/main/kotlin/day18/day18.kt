package day18

import AoCLib.SomeDay
import AoCLib.toStrs
import java.util.*

object Day18:SomeDay(2020,18) {
    private val rs = """[()\d+*]""".toRegex()

    private fun calc(s: String, opPrecedences:Map<String,Int> = mapOf("+" to 0, "*" to 0)): Long {
        val args = mutableListOf<String>()
        val ops = Stack<String>()
        rs.findAll(s).forEach {
            when (val op = it.value) {
                "(" -> ops.push("(")
                ")" -> {
                    while (ops.peek() != "(")
                        args.add(ops.pop())
                    ops.pop()
                }
                "*","+" -> {
                    while (ops.isNotEmpty()
                            && ops.peek() != "("
                            && opPrecedences.getOrDefault(ops.peek(),0)
                            >= opPrecedences.getOrDefault(op,0))
                        args.add(ops.pop())
                    ops.push(op)
                }
                else -> args.add(op)
            }
        }
        while (ops.isNotEmpty())
            args.add(ops.pop())
        val acc = Stack<Long>()
        while (args.isNotEmpty()){
            acc.push(when (val op = args.removeAt(0)) {
                "+" -> acc.pop() + acc.pop()
                "*" -> acc.pop() * acc.pop()
                else -> op.toLong()
            })
        }
        return acc.pop()
    }

    override fun first(data: String): Any? =
        data.toStrs().map { calc(it) }.sum()
    // 5783053349377 Time: 50ms

    override fun second(data: String): Any? =
        data.toStrs().map { calc(it, mapOf("*" to 0, "+" to 1)) }.sum()
    // 74821486966872 Time: 9ms
}

fun main() = SomeDay.mainify(Day18)