package day01.sums

import AoCLib.toInts
import AoCLib.SomeDay

object Day01: SomeDay(2020,1) {
    override val title = "Report Repair"

    private fun twoSum(nums: IntArray, target: Int): Int {
        val diffs = HashSet<Int>()
        for (num in nums) {
            val diff = target - num
            if (diffs.contains(diff)) return diff * num
            diffs.add(num)
        }
        throw IllegalArgumentException("No solution")
    }
    private fun threeSum(nums: IntArray, target: Int): Int {
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

    override fun first(data: String): Any? {
        return twoSum(data.toInts().toIntArray(), 2020)
    } // 388075 Time: 23ms

    override fun second(data: String): Any? {
        return threeSum(data.toInts().toIntArray(), 2020)
    } // 293450526 Time: 5ms
}

fun main() = SomeDay.mainify(Day01)