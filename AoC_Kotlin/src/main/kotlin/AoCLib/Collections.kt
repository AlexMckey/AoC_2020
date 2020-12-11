package AoCLib

/**
 * Extension functions and utility methods for collections.
 */

/**
 * sum number elements of Pair
 */
fun <T : Number> Triple<T, T, T>.sum() = this.first.toLong() + this.second.toLong() + this.third.toLong()
fun Pair<Int, Int>.sum() = this.first + this.second
fun Pair<Long, Long>.sum() = this.first + this.second

fun <T : Any> List<T>.asInfiniteSequence(): Sequence<T> {
    var index = 0
    return generateSequence(get(index)) { get(++index % size) }
}

fun <T> List<T>.repeat(times: Int): List<T> {
    val sequence = sequence { repeat(times) { yieldAll(this@repeat) } }
    val list = ArrayList<T>(sequence.count())
    sequence.forEach { list.add(it) }
    return list
}

inline fun <T> List<T>.forAllPairs(block: (T, T) -> Unit) {
    for (i in 0 until size) {
        for (j in (i + 1) until size) {
            block(get(i), get(j))
        }
    }
}

inline fun <T> List<T>.forAllUniquePairs(block: (T, T) -> Unit) {
    forAllPairs { i, j ->
        block(i, j)
        block(j, i)
    }
}

fun <T> List<T>.chunkedBy(selector: (T) -> Boolean): List<List<T>> =
    fold(mutableListOf(mutableListOf<T>())) { acc, item ->
        if (selector(item)) acc.add(mutableListOf())
        else acc.last().add(item)
        acc
    }

fun <T> List<T>.headAndTail(): Pair<T, List<T>> = this.first() to this.drop(1)

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

fun <T : Any> List<T>.permute(): List<List<T>> {
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