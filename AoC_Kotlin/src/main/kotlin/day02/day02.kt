package day02

import InputLib.DayInput

fun main() {
    val strs = DayInput.inputStrsList("input02.txt")
    val checkPolicies = strs.map{
        val arr = it.split(": ")
        val policyStr = arr[0].split(" ")
        val counts = policyStr[0].split("-").map { it.toInt() }
        val ch = policyStr[1][0]
        val password = arr[1]
        val passLen = password.length
        val passCharCount = password.count { it == ch }
        val policyPos = counts.filter { it <= passLen }.map { it-1 }
        val passPosCount = password.slice(policyPos).filter { it == ch }.count()
        Triple(arr[1],
            passCharCount >= counts[0] && passCharCount <= counts[1],
            passPosCount == 1)
    }
    val res1 = checkPolicies.filter{it.second}.count()
    println(res1) // 445
    val res2 = checkPolicies.filter{it.third}.count()
    println(res2) // 491
}