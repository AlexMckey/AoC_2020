package day01

import InputLib.DayInput

object day01 {
    fun twoSum(nums: IntArray, target: Int): IntArray {
        val diffs = HashMap<Int,Int>()
        for (idx in nums.indices) {
            val diff = target - nums[idx]
            if (diffs.containsKey(diff)) return intArrayOf(diff,nums[idx])
            diffs[nums[idx]] = idx
        }
        throw IllegalArgumentException("No solution")
    }
    fun threeSum(nums: IntArray, target: Int): IntArray {
        val ia = nums.sorted()
        val len = ia.size
        for (i in 0 .. len - 3) {
            var l = i + 1
            var r = len - 1
            while (l < r) {
                val list = intArrayOf(ia[i], ia[l], ia[r])
                when {
                    list.sum() == target -> return list
                    list.sum() < target -> l++
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
    println(res1.reduce(Int::times)) // 388075
    val res2 = day01.threeSum(expenses, target)
    println(res2.reduce(Int::times)) // 293450526
}