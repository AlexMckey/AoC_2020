package AoCLib

class SimpleTree<T>(val value: T) {
    val children: MutableList<SimpleTree<T>> = mutableListOf()
    val size: Int
        get() = children.fold(1) { acc, tree -> acc + tree.size }
    val height: Int
        get() = 1 + (children.map { it.size }.maxOrNull() ?: 0)
    fun add(child: T) = children.add(SimpleTree(child))
}