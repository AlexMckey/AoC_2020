//val s = "20,0,1,11,6,3"
val s = "0,3,6"
val ns = s.split(',')
val nums = ns.dropLast(1)
    .mapIndexed { index, s ->  s.toInt() to index+1 }
    .toMap().toMutableMap()
var i = nums.size+1
i
var num = ns.last().toInt()
//var t = 0
//num
//nums.containsKey(num)
//nums[num] = i
//num = 0
//println(nums)
//i++
//num
//nums.containsKey(num)
//t = i - nums[num]!!
//nums[num] = i
//num = t
//println(nums)
//i++
//num
//nums.containsKey(num)
//t = i - nums[num]!!
//nums[num] = i
//num = t
//println(nums)
//i++
//do {
//    if (nums.containsKey(num)) {
//        t = i - nums[num]!!
//        nums[num] = i
//        num = t
//    } else {
//        nums[num] = i
//        num = 0
//    }
//    println(nums)
//    i++
//} while (i < 2020)
//num
tailrec fun elvesGame(nums: MutableMap<Int,Int>, num: Int, i: Int, endCnt: Int = 2020): Int {
    if (i >= endCnt) return num
    return if (!nums.containsKey(num)) {
        nums[num] = i
        elvesGame(nums, 0, i + 1)
    } else {
        val t = i - nums[num]!!
        nums[num] = i
        elvesGame(nums, t, i + 1)
    }
}
elvesGame(nums,num,nums.size+1)