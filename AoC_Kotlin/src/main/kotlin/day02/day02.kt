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
        val passCharCount = password.groupingBy { it }.eachCount()
        val chInPassword = passCharCount.containsKey(ch)
        val chCheckLowCount = chInPassword && counts[0] <= passCharCount[ch]!!
        val chCheckUpperCount = chInPassword && counts[1] >= passCharCount[ch]!!
        val chCheckFirstPos = passLen >= counts[0] && password[counts[0]-1] == ch
        val chCheckSecondPos = passLen >= counts[1] && password[counts[1]-1] == ch
        Triple(arr[1],
            chCheckLowCount && chCheckUpperCount,
            (chCheckFirstPos || chCheckSecondPos) && !(chCheckFirstPos && chCheckSecondPos))
    }
    val res1 = checkPolicies.filter{it.second}.count()
    println(res1) // 445
    val res2 = checkPolicies.filter{it.third}.count()
    println(res2) // 491
}