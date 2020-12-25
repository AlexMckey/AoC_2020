val l1 = mutableListOf(3,8,9,1,2,5,4,6,7)
val l2 = mutableListOf(2,8,9,1,5,4,6,7,3)
val l = l2
val max = l.maxOrNull()!!
var cur = l.first()-1
cur
val sl = l.drop(1).take(3)
l.removeAll(sl)
sl
l
while(cur > 0 && sl.contains(cur)){cur--}
cur
if (cur == 0) {
    cur = max
    while(cur > 1 && sl.contains(cur)){cur--}
}
cur
val f = l.indexOf(cur)
f
l.addAll(f,sl)
l

Int.MAX_VALUE
val ml = l1.maxOrNull()!!
val r1 = ((ml+1)..ml)
r1
l1 + ml
