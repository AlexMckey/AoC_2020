val s = "0,3,6"
fun elvesGame(s: String,endCnt:Int): List<Int> {
    val ns = s.split(',').map { it.toInt() }.toList()
    tailrec fun recHelper(nums: List<Int>, num: Int, i: Int): List<Int> {
        if (i > endCnt) return nums
        val idx = nums.lastIndexOf(num)
        return if (idx >= 0)
            recHelper(nums + num, i - idx - 1, i + 1)
        else
            recHelper(nums + num, 0, i + 1)
    }
    return recHelper(ns.dropLast(1), ns.last(), ns.size)
}
elvesGame(s,10000).last()