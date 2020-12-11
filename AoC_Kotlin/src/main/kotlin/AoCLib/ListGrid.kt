package AoCLib

typealias ListGrid<T> = List<List<T>>
typealias MutableListGrid<T> = List<MutableList<T>>

fun <T> ListGrid<T>.mutable() = this.map { it.toMutableList() }

fun List<String>.toListGrid() = this.map { it.toCharArray().toList() }

/**
 * Safe transpose a list of unequal-length lists.
 *
 * Example:
 * transpose(List(List(1, 2, 3), List(4, 5, 6), List(7, 8)))
 * -> List(List(1, 4, 7), List(2, 5, 8), List(3, 6))
 */
fun <T> transpose(xs: ListGrid<T>): ListGrid<T> {
    // Helpers
    fun <T> List<T>.head(): T = this.first()
    fun <T> List<T>.tail(): List<T> = this.takeLast(this.size - 1)
    fun <T> T.append(xs: List<T>): List<T> = listOf(this).plus(xs)

    xs.filter { it.isNotEmpty() }.let { ys ->
        return when (ys.isNotEmpty()) {
            true -> ys.map { it.head() }.append(transpose(ys.map { it.tail() }))
            else -> emptyList()
        }
    }
}

/**
 * Finds first entry equal to [value] in matrix
 */
fun <T> ListGrid<T>.find(value: T): Point? {
    forEachIndexed { y, row ->
        row.forEachIndexed { x, curr ->
            if (curr == value) {
                return Point(x, y)
            }
        }
    }
    return null
}

operator fun <T> MutableListGrid<T>.set(v: Point, value: T) { when {
        v.y !in indices -> throw IndexOutOfBoundsException()
        v.x !in this[v.y].indices -> throw IndexOutOfBoundsException()
        else -> this[v.y][v.x] = value
    }
}
operator fun <T> MutableListGrid<T>.set(i: Int, j: Int, value: T)
    { this[i][j] = value }

operator fun <T> ListGrid<T>.get(v: Point): T? = when {
    v.y !in indices -> null
    v.x !in this[v.y].indices -> null
    else -> this[v.y][v.x]
}
operator fun <T> ListGrid<T>.get(x: Int, y: Int): T? = get(Point(x,y))

fun <T> ListGrid<T>.inBounds(x: Int, y: Int) = y in indices && x in this[y].indices
operator fun <T> ListGrid<T>.contains(p: Point) = this.inBounds(p.x,p.y)

@JvmName("boundsString")
fun List<String>.bounds(): Pair<IntRange,IntRange> =
    this.first().indices to this.indices

fun <T> ListGrid<T>.bounds(): Pair<IntRange,IntRange> =
    this.first().indices to this.indices