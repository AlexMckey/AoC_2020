package day14

import AoCLib.SomeDay
import AoCLib.toStrs

object Day14: SomeDay(2020,14) {
    private val rmask = """mask = ([X10]+)""".toRegex()
    private val rmem = """mem\[(\d+)] = (\d+)""".toRegex()
    private var mask: String = ""
    private fun Long.maskedArg(mask: String): Long {
        val maskOr = mask.replace('X','0').toLong(2)
        val maskAnd = mask.replace('X','1').toLong(2)
        return this.or(maskOr).and(maskAnd)
    }
    override fun first(data: String): Any? {
        val memory = mutableMapOf<Int,Long>()
        data.toStrs().forEach {
            if (rmask.matches(it))
                mask = rmask.matchEntire(it)!!.groupValues[1]
            else {
                val (addr, arg) = rmem.find(it)!!.destructured
                memory[addr.toInt()] = arg.toLong().maskedArg(mask)
            }
        }
        return memory.values.sum()
    } // 11884151942312 Time: 38ms

    private fun Long.maskedAddr(mask: String): List<Long> {
        val sb = StringBuilder(this.toString(2).padStart(36,'0'))
        val posX = mask.withIndex().filter { it.value == 'X' }.map { it.index }
        val pos1 = mask.withIndex().filter { it.value == '1' }.map { it.index }
        val floatBit = (0 until 1.shl(posX.size)).map { it.toString(2).padStart(posX.size,'0') }
        val res = floatBit.map { it.toCharArray().zip(posX) }
            .map { list -> list.forEach { pair -> sb[pair.second] = pair.first }
                pos1.forEach { sb[it] = '1' }
                sb.toString()
            }
        return res.map { it.toLong(2) }
    }

    override fun second(data: String): Any? {
        val memory = mutableMapOf<Long,Long>()
        data.toStrs().forEach {
            if (rmask.matches(it))
                mask = rmask.matchEntire(it)!!.groupValues[1]
            else {
                val (addr, arg) = rmem.find(it)!!.destructured
                addr.toLong().maskedAddr(mask)
                    .forEach { memory[it] = arg.toLong() }
            }
        }
        return memory.values.sum()
    } // 2625449018811 Time: 215ms
}

fun main() = SomeDay.mainify(Day14)