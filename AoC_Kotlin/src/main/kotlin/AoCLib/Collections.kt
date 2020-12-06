package AoCLib

/**
 * Extension functions and utility methods for collections.
 */

/**
 * Generates the sequence of all permutations of items in current list.
 */
fun <T : Any> List<T>.permutations(): Sequence<List<T>> =
    if (size == 1) sequenceOf(this) else {
        val iterator = iterator()
        var head = iterator.next()
        var permutations = (this - head).permutations().iterator()

        fun nextPermutation(): List<T>? = if (permutations.hasNext()) listOf(head) + permutations.next() else {
            if (iterator.hasNext()) {
                head = iterator.next()
                permutations = (this - head).permutations().iterator()
                nextPermutation()
            } else null
        }
        generateSequence { nextPermutation() }
    }

fun <T: Any> List<T>.permute(): List<List<T>> {
    if (this.size == 1) return listOf(this)
    val perms = mutableListOf<List<T>>()
    val sub = this[0]
    for (perm in this.drop(1).permute())
        for (i in 0..perm.size) {
            val newPerm = perm.toMutableList()
            newPerm.add(i, sub)
            perms.add(newPerm)
        }
    return perms
}

fun <T : Any> List<T>.pairs(): Sequence<Pair<T, T>> {
    require(size > 1)
    return sequence {
        for (x in indices) {
            for (y in x + 1 until size) {
                yield(get(x) to get(y))
            }
        }
    }
}

/**
 * Splits iterable into list of subsequences such that each subsequence contains only equal items.
 */
fun <T> Iterable<T>.series(): List<List<T>> {
    val list = mutableListOf<MutableList<T>>()
    var store = mutableListOf<T>()
    for (element in this) {
        if (store.contains(element)) {
            store.add(element)
        } else {
            store = mutableListOf(element)
            list.add(store)
        }
    }
    return list
}

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