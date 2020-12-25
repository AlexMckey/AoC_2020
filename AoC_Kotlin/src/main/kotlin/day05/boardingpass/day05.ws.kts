val id = "BFFFBBFRRR\n" +
        "FFFBBBFRRR\n" +
        "BBFFBBFRLL\n" +
        "FBFBBFFRLR"
val dd = id.split("\n").sorted()
val d = dd[0].map {
    when (it) {
        'F', 'L' -> -1
        else -> 1
    }
}
d
val rows = d.take(7)
val cols = d.drop(7)
val r = rows.fold(64 to 64) { (acc,seed), i -> (acc + seed / 2 * i to seed / 2) }.first - 1
r
val c = cols.fold(4 to 4) { (acc, seed), i -> acc + seed / 2 * i to seed / 2 }.first - 1
c
r * 8 + c
fun calcSeatID(s: String): Int {
    fun posToKoef(ch: Char): Int = when (ch) {
        'B', 'R' -> 1
        else -> -1
    }
    fun calcPos(l: String, seed: Int): Int  =
        l.fold(seed to seed) {(acc,seed), i -> acc + seed / 2 * posToKoef(i) to seed / 2}.first-1
    return calcPos(s.take(7),128)/2 * 8 + calcPos(s.drop(7),8)/2
}
calcSeatID(dd[0])
dd.map { calcSeatID(it) }