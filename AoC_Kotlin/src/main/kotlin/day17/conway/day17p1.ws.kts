import AoCLib.Point3D

val s = ".#.\n" +
        "..#\n" +
        "###"
var cube = s.lines()
    .flatMapIndexed{ y, l -> l
        .mapIndexed { x, ch ->
            if (ch == '#') Point3D(x,y,0)
            else null
        } }.filterNotNull().toSet()
cube
val p1 = cube.drop(1).first()
p1
p1.near().intersect(cube)
var survivors = cube.filter { it.near().intersect(cube).size in 2..3 }
var born = cube.flatMap { it.near() }.groupingBy { it }.eachCount().filter { it.value == 3 }.map { it.key }
var newPoints = born.union(survivors)
newPoints.count()
cube = newPoints
survivors = cube.filter { it.near().intersect(cube).size in 2..3 }
born = cube.flatMap { it.near() }.groupingBy { it }.eachCount().filter { it.value == 3 }.map { it.key }
newPoints = born.union(survivors)
newPoints.count()
