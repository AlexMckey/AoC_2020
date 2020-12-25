val cpk = 5764801
val dpk = 17807724
val sn = 7
val m = 20201227
val v = 1
fun calcLoopSize(pubKey: Int, subj: Int, mod: Int = 20201227): Int =
    generateSequence(1 to 1) {
        it.first+1 to it.second * subj % mod
    }.dropWhile { it.second != pubKey }.first().first-1

val cls = calcLoopSize(cpk,sn)
cls
val dls = calcLoopSize(dpk,sn)
dls
fun findKey(subj: Int, loopSize: Int, mod: Int = 20201227): Long {
    var cur = 1L
    repeat(loopSize) {
        cur = cur * subj % mod
    }
    return cur
}
findKey(sn,cls)
findKey(sn,dls)
findKey(dpk,cls)
findKey(cpk,dls)