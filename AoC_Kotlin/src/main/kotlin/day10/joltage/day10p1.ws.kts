val s1 = "16\n" +
        "10\n" +
        "15\n" +
        "5\n" +
        "1\n" +
        "11\n" +
        "7\n" +
        "19\n" +
        "6\n" +
        "12\n" +
        "4"
val d1_ = s1.split('\n').map { it.toInt() }
val d1 = d1_ + 0 + (d1_.maxOrNull()!! + 3)
d1.sorted().zipWithNext { a, b -> b - a }.groupingBy { it }.eachCount()
val s2 = "28\n" +
        "33\n" +
        "18\n" +
        "42\n" +
        "31\n" +
        "14\n" +
        "46\n" +
        "20\n" +
        "48\n" +
        "47\n" +
        "24\n" +
        "23\n" +
        "49\n" +
        "45\n" +
        "19\n" +
        "38\n" +
        "39\n" +
        "11\n" +
        "1\n" +
        "32\n" +
        "25\n" +
        "35\n" +
        "8\n" +
        "17\n" +
        "7\n" +
        "9\n" +
        "4\n" +
        "2\n" +
        "34\n" +
        "10\n" +
        "3"
val d2_ = s2.split('\n').map { it.toInt() }
val d2 = d2_ + 0 + (d2_.maxOrNull()!! + 3)
d2.sorted().zipWithNext { a, b -> b - a }.groupingBy { it }.eachCount()
fun joltDifferences(jolts: List<Int>): Int {
    val t = jolts + 0 + (jolts.maxOrNull()!! + 3)
    val res = t.sorted().zipWithNext { a, b -> b - a }.groupingBy { it }.eachCount()
    return res[1]!! * res[3]!!
}
joltDifferences(d1_)
joltDifferences(d2_)