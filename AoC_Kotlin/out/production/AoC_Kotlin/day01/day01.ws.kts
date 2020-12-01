val strs = "1721\n" +
        "979\n" +
        "366\n" +
        "299\n" +
        "675\n" +
        "1456"
val ints = strs.lines().map(String::toInt)
val es = ints.toIntArray()
es.toList()
fun twoSum(nums: IntArray, target: Int): IntArray {
    val diffs = HashMap<Int,Int>()
    for (idx in nums.indices) {
        val diff = target - nums[idx]
        if (diffs.containsKey(diff)) return intArrayOf(diff,nums[idx])
        diffs[nums[idx]] = idx
    }
    throw IllegalArgumentException("No solution")
}
fun threeSum(nums: IntArray, target: Int): IntArray {
    val ia = nums.sorted()
    val len = ia.size
    for (i in 0 .. len - 3) {
        var l = i + 1
        var r = len - 1
        while (l < r) {
            val list = intArrayOf(ia[i], ia[l], ia[r])
            when {
                list.sum() == target -> return list
                list.sum() < target -> l++
                else -> r--
            }
        }
    }
    throw IllegalArgumentException("No solution")
}
val res1 = twoSum(es,2020)
res1.reduce(Int::times)
res1[0]*res1[1]
val res2 = threeSum(es, 2020)
res2.toList()
res2.reduce(Int::times)