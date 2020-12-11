package AoCLib

object Grid {
    fun List<String>.toGrid() = this
        .flatMapIndexed { col, line ->
            line.mapIndexed { row, char ->
                Point(row, col) to char
            }
        }
        .associate { it }

    /**
     * Safe transpose a list of unequal-length lists.
     *
     * Example:
     * transpose(List(List(1, 2, 3), List(4, 5, 6), List(7, 8)))
     * -> List(List(1, 4, 7), List(2, 5, 8), List(3, 6))
     */
    fun <T> transpose(xs: List<List<T>>): List<List<T>> {
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
    fun <T> List<List<T>>.find(value: T): Point? {
        forEachIndexed { y, row ->
            row.forEachIndexed { x, curr ->
                if (curr == value) {
                    return Point(x, y)
                }
            }
        }
        return null
    }

    operator fun <T> List<MutableList<T>>.set(v: Point, value: T) {
        when {
            v.y !in indices -> throw IndexOutOfBoundsException()
            v.x !in this[v.y].indices -> throw IndexOutOfBoundsException()
            else -> this[v.y][v.x] = value
        }
    }

    operator fun <T> List<MutableList<T>>.set(i: Int, j: Int, value: T) {
        this[i][j] = value
    }

    operator fun <T> List<List<T>>.get(v: Point): T? = when {
        v.y !in indices -> null
        v.x !in this[v.y].indices -> null
        else -> this[v.y][v.x]
    }

    fun <T> List<List<T>>.fit(v: Point) = v.y in indices && v.x in this[v.y].indices

    @JvmName("boundsString")
    fun List<String>.bounds(): Pair<IntRange,IntRange> =
        this.first().indices to this.indices

    fun <T> List<List<T>>.bounds(): Pair<IntRange,IntRange> =
        this.first().indices to this.indices
}