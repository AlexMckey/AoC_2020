package AoCLib

data class Point3D(val x: Int = 0, val y: Int = 0, val z: Int = 0): Nears<Point3D>() {
    override fun toString(): String = "[x:$x,y:$y,z:$z]"
    operator fun plus(other: Point3D) = Point3D(other.x + x, other.y + y, other.z + z)
    operator fun plus(other: Triple<Int, Int, Int>) = Point3D(other.first + x, other.second + y, other.third + z)
    operator fun minus(other: Point3D) = Point3D(other.x - x, other.y - y, other.z - z)
    operator fun times(other: Int) = Point3D(x * other, y * other, z * other)
    override fun near(): List<Point3D> = r
        .flatMap { dx -> r
            .flatMap { dy -> r
                .map { dz ->
                    if (dx == 0 && dy == 0 && dz == 0) null else this + Triple(dx,dy,dz)
            } } }.filterNotNull()
}