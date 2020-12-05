val i = listOf(10,9,8,7,5,4,3,2,1)
val idx = i.zipWithNext { a, b -> a - b }.indexOf(2)
idx
i.first()
val seatID = i.first()-idx-1
seatID