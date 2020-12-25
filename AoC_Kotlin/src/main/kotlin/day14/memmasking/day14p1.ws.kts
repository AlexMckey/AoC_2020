val rmask = """mask = ([X10]+)""".toRegex()
val rmem = """mem\[(\d+)\] = (\d+)""".toRegex()
val s = "mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X\n" +
        "mem[8] = 11\n" +
        "mem[7] = 101\n" +
        "mem[8] = 0"
val ss = s.lines()
ss[1]
rmask.matches(ss[0])
rmask.matches(ss[1])
rmem.matches(ss[0])
rmem.matches(ss[1])
val res1 = rmask.matchEntire(ss[0])!!.destructured.match
res1.groupValues
val res2 = rmem.matchEntire(ss[1])!!.destructured.match
res2.groupValues
var acc = 0L
var mask = res1.groupValues[1]
val maskOr = mask.replace('X','0').toLong(2)
val maskAnd = mask.replace('X','1').toLong(2)
acc
acc = acc.or(maskOr)
acc = acc.and(maskAnd)
acc
fun masked(acc: Long, mask: String): Long {
    val maskOr = mask.replace('X','0').toLong(2)
    val maskAnd = mask.replace('X','1').toLong(2)
    var res = acc
    res = res.or(maskOr)
    return res.and(maskAnd)
}
masked(11L,mask)
masked(101L,mask)
masked(0L,mask)
val memory = mutableMapOf<Int,Long>()
ss.forEach {
    if (rmask.matches(it))
        mask = rmask.matchEntire(it)!!.groupValues[1]
    else {
        val (addr, arg) = rmem.find(it)!!.destructured
        memory[addr.toInt()] = masked(arg.toLong(),mask)
    }
}
memory.values.sum()