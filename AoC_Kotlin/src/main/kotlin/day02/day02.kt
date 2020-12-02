package day02

import InputLib.DayInput

fun main() {
    val strs = DayInput.inputStrsList("input02.txt")
    val checkPolicies = strs.map{
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
    val res1 = checkPolicies.filter{it.second}.count()
    println(res1) // 445
    val res2 = checkPolicies.filter{it.third}.count()
    println(res2) // 491
}