package day23.cupsgame

import AoCLib.SomeDay
import java.util.*

object Day23Better: SomeDay(2020,23){
    class Node(val data: Int) : Comparable<Node> {
        lateinit var next: Node

        override fun compareTo(other: Node): Int {
            return data.compareTo(other.data)
        }
    }

    val cups = TreeSet<Node>()

    private fun List<Int>.toLinkedList(): Node {
        val head = Node(this[0])
        var prev = head
        cups.add(head)
        for (i in 1 until size) {
            val next = Node(this[i])
            cups.add(next)
            prev.next = next
            prev = next
        }
        prev.next = head
        return head
    }

    override fun first(data: String): Any? {
        val input = readLine()!!.toCharArray()
            .map { it - '0' }
        val origHead = (input + ((input.maxOrNull()!! + 1)..1000000)).toLinkedList()

        var head = origHead
        for (i in 0 until 10000000) {
            val toMove = listOf(head.next, head.next.next, head.next.next.next)
            val next = head.next.next.next.next

            head.next = next
            cups.removeAll(toMove)

            val dest = cups.lower(head) ?: cups.last()
            val after = dest.next
            dest.next = toMove[0]
            toMove[2].next = after

            cups.addAll(toMove)
            head = next
            if (i % 10000 == 0) print("+")
        }
        println()
        val one = cups.floor(Node(1))!!
        println(one.next.data)
        println(one.next.next.data)
        return one.next.data.toLong()*one.next.next.data
    }
}

fun main() = SomeDay.mainify(Day23)
