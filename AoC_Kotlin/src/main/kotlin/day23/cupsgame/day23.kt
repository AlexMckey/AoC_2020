package day23.cupsgame

import AoCLib.SomeDay
import AoCLib.toIntValue

object Day23:SomeDay(2020,23) {
    override val title = "Crab Cups"

    data class Cup(val label: Int) { lateinit var next: Cup }
    class Game(cupsInit: String, realGame: Boolean = false) {
        private val cupMap = mutableMapOf<Int, Cup>()
        private var cur: Cup
        private var maxCupLabel: Int
        init {
            val cupsList = cupsInit.map { it.toIntValue() }
            val head = Cup(0)
            var node = head
            maxCupLabel = cupsList.maxOrNull()!!
            val dopList = if (realGame) ((maxCupLabel+1)..1000000) else emptyList()
            if (realGame) maxCupLabel = 1000000
            (cupsList + dopList).forEach {
                node.next = Cup(it)
                cupMap[it] = node.next
                node = node.next
            }
            node.next = head.next
            cur = head.next
        }
        private fun nextRound() {
            val node = cur.next
            cur.next = cur.next.next.next.next
            val threeCup = listOf(node.label, node.next.label, node.next.next.label)
            var dest = if (cur.label == 1) maxCupLabel else cur.label - 1
            while (dest in threeCup) {
                dest -= 1
                if (dest < 1) dest = maxCupLabel
            }
            val newDest = cupMap[dest]!!
            node.next.next.next = newDest.next
            newDest.next = node
            cur = cur.next
        }
        fun runGame(rounds: Int) {
            repeat(rounds) { nextRound() }
        }
        fun resultList(): List<Int> {
            val fromNode = cupMap[1]!!
            val res = mutableListOf<Int>()
            var node = fromNode
            do {
                res.add(node.label)
                node = node.next
            } while (node != fromNode)
            return res.toList()
        }
        fun resultRealGame(): Long =
            cupMap[1]!!.next.label.toLong() *
            cupMap[1]!!.next.next.label
    }

    override fun first(data: String): Any? {
        val g = Game("123487596")
        //val g = Game("389125467")
        g.runGame(100)
        return g.resultList().drop(1).joinToString("")
    } // 47598263 Time: 16ms

    override fun second(data: String): Any? {
        val g = Game("123487596",true)
        g.runGame(10_000_000)
        return g.resultRealGame()
    } // 248009574232 Time: 1768ms
}

fun main() = SomeDay.mainify(Day23)