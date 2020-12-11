val nums = List(0,0,0,1,1,1,1,1,2,2,3)

def cnt(i: Int) = nums.combinations(i)
  .filter(_.sum == i)
  .map(_.filterNot(_ == 0))
  .flatMap(_.permutations.toList)
  .toList
  .size

(1 to 5).map(cnt)