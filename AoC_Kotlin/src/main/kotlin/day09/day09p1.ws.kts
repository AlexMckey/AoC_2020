import AoCLib.Collections.pairs
import AoCLib.Collections.sum

val l = listOf(35,20,15,25,47,40,62,55,65,95,102,117,150,182,127,219,299,277,309,576)
val w5_1 = l.take(5)
val w5_2 = l.drop(1).take(5)
val w5_3 = l.drop(2).take(5)
w5_1
w5_2
w5_3
val w5pairs1 = w5_1.pairs().map{it.first + it.second}.toList()
val w5pairs2 = w5_2.pairs().map{it.first + it.second}.toList()
val w5pairs3 = w5_3.pairs().map{it.first + it.second}.toList()
w5pairs1
w5pairs2
w5pairs3
w5pairs1.contains(l[5])
w5pairs2.contains(l[6])
w5pairs3.contains(l[7])
l.windowed(6).map {
    it.last() to it.take(5).pairs().map{it.first + it.second}.contains(it.last())
}
l.windowed(6).find {
    val num = it.last()
    val nums = it.take(5)
    !nums.pairs().map { it.sum() }.contains(num)
}?.last()
fun windowsSeq(windowCount: Int) = generateSequence(Triple(0,0,true)) {
    val w = l.subList(it.first,it.first+windowCount+1)
    Triple(it.first + 1,w.last(),w.take(windowCount).pairs().map{it.first + it.second}.contains(w.last()))
}
windowsSeq(5).dropWhile { it.third }.first().second