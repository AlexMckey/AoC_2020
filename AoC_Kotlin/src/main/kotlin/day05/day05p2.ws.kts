val i = listOf(10,9,8,7,5,4,3,2,1)
val idx = i.zipWithNext { a, b -> a - b }.indexOf(2)
idx
i.first()
val seatID = i.first()-idx-1
seatID
//val s = "FBFBBFFRLR"
val s = "BBFFBBFRLL"
fun posToKoef(ch: Char): Int = when (ch) {
    'B', 'R' -> 1
    else -> -1
}
fun calcSeatID(s: String): Int {
    fun calcPos(l: String, seed: Int): Int  =
        l.fold(seed to seed) {(acc,seed), i -> acc + seed / 2 * posToKoef(i) to seed / 2}.first-1
    return calcPos(s,1024) / 2
}
64*8
calcSeatID(s)
val bs = s.replace('B','1')
    .replace('R','1')
    .replace('F','0')
    .replace('L','0')
bs
fun chToOne(ch: Char): Int = if (ch in listOf('B','R')) 1 else 0
val twoPowSeq = generateSequence(1){it*2}
val pows = twoPowSeq.take(s.length)
val ps = pows.toList().reversed()
bs.map { it - '0' }.zip(ps).map { (a,b) -> a*b }.reduce(Int::plus)
s.reversed().map { chToOne(it) }
s.reversed().fold(1 to 0){ (pow2, acc), c -> pow2 * 2 to acc + chToOne(c) * pow2 }.second