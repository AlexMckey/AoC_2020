package AoCLib

data class Point4D(val x: Int = 0, val y: Int = 0, val z: Int = 0, val w: Int = 0): Nears<Point4D>() {
    override fun toString(): String = "[x:$x,y:$y,z:$z, w:$w]"
    operator fun plus(other: Point4D) = Point4D(other.x + x, other.y + y, other.z + z, other.w + w)
    operator fun minus(other: Point4D) = Point4D(other.x - x, other.y - y, other.z - z)
    operator fun times(other: Int) = Point4D(x * other, y * other, z * other)
    override fun near(): List<Point4D> =
        r.flatMap { dx -> r
            .flatMap { dy -> r
                .flatMap { dz -> r
                    .map { dw -> r
                        if (dx == 0 && dy == 0 && dz == 0 && dw == 0) null
                        else Point4D(x+dx,y+dy,z+dz, w+dw)
            } } } }.filterNotNull()
}
