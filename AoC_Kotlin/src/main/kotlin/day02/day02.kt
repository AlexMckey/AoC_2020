package day02

import AoCLib.toStrs
import AoCLib.SomeDay

object Day02: SomeDay(2020,2) {
    fun checkPolicies(strs: List<String>) = strs.map{
        val arr = it.split(": ")
        val policyStr = arr[0].split(" ")
        val ints = policyStr[0].split("-").map { it.toInt() }
        val ch = policyStr[1][0]
        val password = arr[1]
        val passCharCount = password.count { it == ch }
        val passLen = password.length
        val policyPos = ints.filter { it <= passLen }.map { it-1 }
        val passPosCount = password.slice(policyPos).count{ it == ch }
        Triple(arr[1],
            passCharCount >= ints[0] && passCharCount <= ints[1],
            passPosCount == 1)
    }

    override fun first(data: String): Any? {
        return checkPolicies(data.toStrs()).filter{it.second}.count()
    } // 445 Time: 34ms

    override fun second(data: String): Any? {
        return checkPolicies(data.toStrs()).filter{it.third}.count()
    } // 491 Time: 7ms
}

fun main() = SomeDay.mainify(Day02)