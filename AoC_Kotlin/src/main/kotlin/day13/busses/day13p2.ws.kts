import java.math.BigInteger

val s1 = "7,13,x,x,59,x,31,19"
val s2 = "17,x,13,19"
val s3 = "67,7,59,61"
val s4 = "67,x,7,59,61"
val s5 = "67,7,x,59,61"
val s6 = "1789,37,47,1889"
val bs = s6.split(',')
        .withIndex()
        .filter { it.value != "x" }
        .map { it.value.toInt() to it.index }
bs
bs.map { 1068781 % it.first+it.second }
val max = bs.maxByOrNull { it.first }!!
max
val bss = bs.map {
    val t = it.second - max.second
    it.first to if (t <= 0) t else t - it.first
}
bss
bss.map { (1068781+4) % it.first to it.second }
fun maxSeqs(from: BigInteger) = generateSequence(from) { it + from }
maxSeqs(max.first.toBigInteger()).take(10).toList()
fun checkSeqs(sii: List<Pair<Int,Int>>, checkVal: BigInteger):Boolean =
        sii.all { checkVal.mod(it.first.toBigInteger()) + it.second.toBigInteger() == BigInteger.valueOf(0)}
checkSeqs(bss, BigInteger.valueOf(3420))
val res = maxSeqs(max.first.toBigInteger()).dropWhile { !checkSeqs(bss,it) }.first()
res-max.second.toBigInteger()