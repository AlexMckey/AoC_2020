import AoCLib.toStrs

val s = "mask = 000000000000000000000000000000X1001X\n" +
        "mem[42] = 100\n" +
        "mask = 00000000000000000000000000000000X0XX\n" +
        "mem[26] = 1"
val rmask = """mask = ([X10]+)""".toRegex()
val rmem = """mem\[(\d+)\] = (\d+)""".toRegex()
val rxmask = """(0|1|X)""".toRegex()
val ss = s.lines()
var mask = StringBuilder()
val res1 = rmask.matchEntire(ss[0])!!.destructured.match
mask.append(res1.groupValues[1])
mask
val res2 = rmem.matchEntire(ss[1])!!.destructured.match
res2.groupValues
var addr = res2.groupValues[1].toLong()
addr
var arg = res2.groupValues[2].toLong()
arg
val xpos = mask.withIndex().filter { it.value == 'X' }.map { it.index }
xpos
val floatBit = (0 .. xpos.size+1).map { it.toString(2).padStart(2,'0') }
floatBit
floatBit.map { it.toCharArray().zip(xpos) }
    .map { list -> list.forEach { pair -> mask[pair.second] = pair.first }; mask.toString() }
fun maskedAddr(arg: Long, mask: String): List<Long> {
    val sb = StringBuilder(arg.toString(2).padStart(36,'0'))
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
val n1 = maskedAddr(42,"000000000000000000000000000000X1001X")
n1
val n2 = maskedAddr(26,"00000000000000000000000000000000X0XX")
n2
val memory = mutableMapOf<Long,Long>()
var msk = ""
s.toStrs().forEach {
    if (rmask.matches(it))
        msk = rmask.matchEntire(it)!!.groupValues[1]
    else {
        val (addr, arg) = rmem.find(it)!!.destructured
        maskedAddr(addr.toLong(),msk)
            .forEach { memory[it] = arg.toLong() }
    }
}
memory.values.sum()