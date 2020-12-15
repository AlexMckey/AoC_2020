package day15

import AoCLib.SomeDay

object Day15:SomeDay(2020,15) {
    private tailrec fun elvesGame(nums: MutableMap<Int,Int>, num: Int, i: Int, endCnt: Int): Int {
        if (i >= endCnt) return num
        val t = if (!nums.containsKey(num)) 0
        else i - nums[num]!!
        nums[num] = i
        return elvesGame(nums, t, i + 1, endCnt)
    }

    override fun first(data: String): Any? {
        val ns = data.split(',')
        val nums = ns.dropLast(1)
            .mapIndexed { index, s ->  s.toInt() to index+1 }
            .toMap().toMutableMap()
        return elvesGame(nums,ns.last().toInt(),nums.size+1,2020)
    }

    override fun second(data: String): Any? {
        //val s = "3,1,2"
        val ns = data.split(',')
        val nums = ns.dropLast(1)
            .mapIndexed { index, s ->  s.toInt() to index+1 }
            .toMap().toMutableMap()
        return elvesGame(nums,ns.last().toInt(),nums.size+1,30000000)
    }
}

fun main() = SomeDay.mainify(Day15)