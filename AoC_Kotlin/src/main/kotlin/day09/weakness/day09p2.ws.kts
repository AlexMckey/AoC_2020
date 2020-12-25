import AoCLib.pairs
import AoCLib.sum

val l = listOf(35,20,15,25,47,40,62,55,65,95,102,117,150,182,127,219,299,277,309,576)
    .map { it.toLong() }
fun windowsSeq(windowCount: Int, seq: List<Long>) = generateSequence(Triple(0,0L,true)) {
    val w = seq.subList(it.first,it.first+windowCount+1)
    Triple(it.first + 1,w.last(),w.take(windowCount).pairs().map{it.first + it.second}.contains(w.last()))
}
val res = windowsSeq(5,l).dropWhile { it.third }.first()
val resNum = res.second
resNum
val w = l.take(res.first+5-1)
fun checkWindow(winNums: List<Long>, winCnt: Int, num: Long) =
    winNums.asSequence()
        .windowed(winCnt)
        .map { it.sum() to it }
        .find { it.first == num }
fun findSeqs4Num(winNums: List<Long>, maxWindows: Int, num: Long): List<Long>? {
    var i = 2
    var res: Pair<Long, List<Long>>?
    do {
        res = checkWindow(winNums,i,num)
        i++
    } while (res == null && i < maxWindows)
    return res?.second
}
val w2 = w.asSequence().windowed(2).map { it.sum() }.filter { it <= resNum }
w2.toList()
checkWindow(w,2,resNum)
val w3 = w.asSequence().windowed(3).map { it.sum() }.filter { it <= resNum }
w3.toList()
checkWindow(w,3,resNum)
val w4 = w.asSequence().windowed(4).map { it.sum() }.filter { it <= resNum }
w4.toList()
checkWindow(w,4,resNum)
findSeqs4Num(w,5,resNum)