package day15

import AoCLib.SomeDay

object Day15:SomeDay(2020,15) {
    fun elvesGameList(s: String, endCnt:Int): Int {
        val ns = s.split(',').map { it.toInt() }.toMutableList()
        val num = ns.removeAt(ns.lastIndex)
        tailrec fun recHelper(num: Int, i: Int): Int {
            if (i >= endCnt) return num
            val idx = ns.lastIndexOf(num)
            ns.add(num)
            return recHelper(if (idx >= 0) i - idx - 1 else 0, i + 1)
        }
        return recHelper(num, ns.size+1)
    }

    override fun first(data: String): Any? =
        elvesGameList(data,2020)
    // 421 Time: 10ms

    fun elvesGameMap(s: String, endCnt: Int): Int {
        val ns = s.split(',')
            .mapIndexed { index, i ->  i.toInt() to index + 1 }
        val (num, idx) = ns.last()
        val nums = ns.dropLast(1)
            .toMap().toMutableMap()
        tailrec fun recHelper(num: Int, i: Int): Int {
            if (i >= endCnt) return num
            val t = i - nums.getOrDefault(num,i)
            nums[num] = i
            return recHelper(t, i + 1)
        }
        return recHelper(num, idx)
    }

    override fun second(data: String): Any? =
        elvesGameMap(data,30000000)
    // 436 Time: 4041ms
}

fun main() = SomeDay.mainify(Day15)