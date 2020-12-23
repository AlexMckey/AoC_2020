package day23

import AoCLib.SomeDay
import AoCLib.toIntValue

object Day23_fast: SomeDay(2020, 23) {
    override fun first(data: String): Any? {
        val nums = data.map { it.toIntValue() }
        val arr = IntArray(9)
        for ((i, x) in nums.withIndex()) arr[x - 1] = nums[(i + 1) % nums.size] - 1
        var x = nums.first() - 1
        repeat(100) { x = step(arr, x) }
        return generateSequence(arr[0]) { arr[it] }
            .takeWhile { it != 0 }
            .fold(0) { acc, y -> 10 * acc + y + 1 }
    } // 47598263 Time: 29ms

    override fun second(data: String): Any? {
        val nums = data.map { it.toIntValue() }
        val arr = IntArray(1000000) { it + 1 }
        for ((i, x) in nums.withIndex()) arr[x - 1] = nums.getOrElse(i + 1) { 10 } - 1
        var x = nums.first() - 1
        arr[arr.lastIndex] = x
        repeat(10000000) { x = step(arr, x) }
        val y = arr[0]
        val z = arr[y]
        return (y + 1).toLong() * (z + 1).toLong()
    } // 248009574232 Time: 311ms

    private fun step(arr: IntArray, x: Int): Int {
        val a = arr[x]
        val b = arr[a]
        val c = arr[b]
        val y = arr[c]
        var t = x
        do {
            t = if (t > 0) t - 1 else arr.lastIndex
        } while (t == a || t == b || t == c)
        val u = arr[t]
        arr[x] = y
        arr[t] = a
        arr[c] = u
        return y
    }
}

fun main() = SomeDay.mainify(Day23_fast)