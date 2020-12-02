package day01

import InputLib.DayInput

object day01 {
    fun twoSum(nums: IntArray, target: Int): Int {
        val diffs = HashSet<Int>()
        for (num in nums) {
            val diff = target - num
            if (diffs.contains(diff)) return diff * num
            diffs.add(num)
        }
        throw IllegalArgumentException("No solution")
    }
    fun threeSum(nums: IntArray, target: Int): Int {
        val ia = nums.sorted()
        val len = ia.size
        for (i in 0 .. len - 3) {
            var l = i + 1
            var r = len - 1
            while (l < r) {
                val sum = ia[i] + ia[l] + ia[r]
                when {
                    sum == target -> return ia[i] * ia[l] * ia[r]
                    sum < target -> l++
                    else -> r--
                }
            }
        }
        throw IllegalArgumentException("No solution")
    }
}

fun main() {
    val expenses = DayInput.inputIntsArray("input01.txt")
    val target = 2020
    val res1 = day01.twoSum(expenses, target)
    println(res1) // 388075
    val res2 = day01.threeSum(expenses, target)
    println(res2) // 293450526
}