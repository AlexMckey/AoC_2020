package day23.cupsgame

import AoCLib.SomeDay

object Day23_p1: SomeDay(2020,23) {
    class Node<T>(val value: T){
        var next: Node<T>? = null
    }
    class CircleList<T>(pars: List<T>) {
        private var cur: Node<T>? = null
        init {
            val head = Node(pars.first())
            var node = head
            pars.forEach {
                node.next = Node(it)
                node = node.next!!
            }
            cur = head.next
            node.next = cur
            head.next = null
        }
        val peekCur get() = cur!!.value
        fun next() {
            cur = cur!!.next
        }
        private var head: Node<T>? = null
        var isEmpty:Boolean = head == null
        fun clear() {
            head = null
        }
        fun find(search: T): Node<T>? {
            var node = cur
            do {
                if (node!!.value == search) return node
                node = node!!.next
            } while (node != cur)
            return null
        }
        fun takeTriple(): List<T> {
            val res = mutableListOf<T>()
            var next = cur!!.next
            repeat(3){
                res.add(next!!.value)
                next = next!!.next
            }
            cur!!.next = next
            return  res
        }
        fun putTriple(afterNode: Node<T>?, lst: List<T>) {
            if (afterNode == null) return
            val next = afterNode!!.next
            var node = afterNode
            lst.forEach {
                node!!.next = Node(it)
                node = node!!.next!!
            }
            node!!.next = next
        }
        fun takeAllFrom(fromNode: Node<T>?): List<T> {
            if (fromNode == null) return emptyList()
            val res = mutableListOf<T>()
            var node = fromNode!!.next
            do {
                res.add(node!!.value)
                node = node.next
            } while (node != fromNode)
            res.add(fromNode.value)
            return  res.toList()
        }
        override fun toString(): String {
            val sb = StringBuilder("[")
            var node = cur
            do {
                sb.append("${node!!.value}")
                node = node.next
                if (node != cur) { sb.append(", ") }
            } while (node != cur)
            return sb.append("]").toString()
        }
    }

    class CrubGame(lst: List<Int>, realGame: Boolean = false){
        private var cl: CircleList<Int>
        private var rgFlag: Boolean = realGame
        private val max = lst.maxOrNull()!!
        var round = 0
        val result get() =
            if (!rgFlag) resultList().dropLast(1).joinToString("")
            else {
                val v1 = cl.find(1)
                val n1 = v1!!.next!!.value
                val n2 = v1!!.next!!.next!!.value
                n1.toLong()*n2
            }
        init {
            if (!rgFlag) cl = CircleList(lst)
            else
                cl = CircleList(lst + ((lst.maxOrNull()!!+1)..1000000))
        }
        fun resultList(): List<Int> = cl.takeAllFrom(cl.find(1))
        fun nextRound() {
            var num = cl.peekCur-1
            val lst = cl.takeTriple()
            while (num > 0 && lst.contains(num)) num--
            if (num == 0) {
                num = max
                while (lst.contains(num)) num--
            }
            val node = cl.find(num)
            cl.putTriple(node,lst)
            cl.next()
            round++
        }
        fun runGame(rounds: Int) {
            repeat(rounds) {
                nextRound()
                if (round % 10000 == 0) print("+")
            }
        }
    }

    override fun first(data: String): Any? {
        val lst = data.toList().map { it-'0' }
        val crubGame = CrubGame(lst)
        crubGame.runGame(100)
        return crubGame.result
    }

    override fun second(data: String): Any? {
        val lst = data.toList().map { it-'0' }
        val crubGame = CrubGame(lst,realGame = true)
        crubGame.runGame(10000000)
        return crubGame.result
    }
}

fun main() = SomeDay.mainify(Day23)