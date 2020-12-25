import day19.*
import day19.grammar.Day19_Bad

val s = "0: 1 2\n" +
        "1: \"a\"\n" +
        "2: 1 3 | 3 1\n" +
        "3: \"b\""
val s1 = "0: 4 1 5\n" +
        "1: 2 3 | 3 2\n" +
        "2: 4 4 | 5 5\n" +
        "3: 4 5 | 5 4\n" +
        "4: \"a\"\n" +
        "5: \"b\""
val sr = """^(\d+): (?:(?:"(\w)")|((?:\d+\s?)+)|(?:((?:\d+\s?)+)\s\|\s((?:\d+\s?)+)))${'$'}""".toRegex()
var m = s1.lines().flatMap { sr.findAll(it).map {
    val (id, ch, sr, ar1, ar2) = it.destructured
    id.toInt() to when {
        ch.isNotEmpty() -> Day19_Bad.Lit(ch.first())
        sr.isNotEmpty() -> Day19_Bad.Sr(sr.split(' ').map { Day19_Bad.Id(it.toInt())})
        ar1.isNotEmpty() -> Day19_Bad.Ar(
            Day19_Bad.Sr(ar1.split(' ').map { Day19_Bad.Id(it.toInt())}),
            Day19_Bad.Sr(ar2.split(' ').map { Day19_Bad.Id(it.toInt())}))
        else -> Day19_Bad.OldRule()
    }
}}.toMap()
m
var res = m[0]!!.replace(m)
res
res.size()
res.isFlat()
//m[3]?.flatten()
//m[1]?.flatten()
//val res2 = m[2]?.replace(m)
//res2?.flatten()
//res.flatten()
val resf = res.flatten()
resf
val c = "ababbb\n" +
        "bababa\n" +
        "abbbab\n" +
        "aaabbb\n" +
        "aaaabbb"
val cs = c.lines()
cs.count { resf.contains(it) }
val mp = mapOf(1 to "1", 2 to "2", 3 to "3")
val mpDop = mapOf(4 to "4", 3 to "5")
mp + mpDop