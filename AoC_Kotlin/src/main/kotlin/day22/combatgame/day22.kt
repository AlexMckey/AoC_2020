package day22.combatgame

import AoCLib.SomeDay
import AoCLib.splitByBlankLines

object Day22:SomeDay(2020,22) {
    override val title = "Crab Combat"

    data class Deck(val player: String, val cards: MutableList<Int>) {
        val score get() = cards.reversed().withIndex().sumOf{ it.value * (it.index+1L) }
    }

    data class Game(val p1: Deck, val p2: Deck, val isRecursiveCombat: Boolean = false) {
        private val cache = mutableListOf<Int>()
        var winner: Int = 0
            private set
        var winnerDeck: Deck? = null
            private set
        val score: Long get() = winnerDeck?.score ?: 0L
        private fun calcHash(d1: Deck, d2: Deck): Int = d1.hashCode() + d2.hashCode()
            //"${d1.cards.hashCode()}:${d2.cards.hashCode()}".hashCode()
        private fun whoWin(p1card: Int, p2card: Int): Int =
            if (!isRecursiveCombat)
                if (p1card > p2card) 1 else 2
            else {
                if (p1.cards.size >= p1card && p2.cards.size >= p2card) {
                    val inngerGame = Game(
                        p1.copy(cards = p1.cards.take(p1card).toMutableList()),
                        p2.copy(cards = p2.cards.take(p2card).toMutableList()),
                        true)
                    inngerGame.runGame()
                    inngerGame.winner }
                else if (p1card > p2card) 1 else 2}
        private fun nextRound() {
            if (cache.contains(calcHash(p1,p2))) {
                p2.cards.clear()
                return
            } else cache.add(calcHash(p1,p2))
            val p1card = p1.cards.removeAt(0)
            val p2card = p2.cards.removeAt(0)
            val winner = whoWin(p1card,p2card)
            val (roundWinner, roundCards) =
                if (winner == 1)
                    p1 to listOf(p1card,p2card)
                else p2 to listOf(p2card,p1card)
            roundWinner.cards.addAll(roundWinner.cards.size, roundCards)
        }
        fun runGame() {
            while (p1.cards.isNotEmpty() && p2.cards.isNotEmpty()) {
                nextRound()
            }
            winner = if (p1.cards.isEmpty()) 2 else 1
            winnerDeck = if (p1.cards.isEmpty()) p2 else p1
        }
        companion object {
            fun parseDeck(s: String): Deck {
                val parts = s.lines()
                val name = parts.first().dropLast(1)
                val cards = parts.drop(1).map { it.toInt() }
                return Deck(name, cards.toMutableList())
            }
        }
    }

    override fun first(data: String): Any? {
        val parts = data.splitByBlankLines()
        val g = Game(Game.parseDeck(parts.first()), Game.parseDeck(parts.last()))
        g.runGame()
        return g.score
    } // 32677 Time: 29ms

    override fun second(data: String): Any? {
        val parts = data.splitByBlankLines()
        val g = Game(Game.parseDeck(parts.first()), Game.parseDeck(parts.last()),true)
        g.runGame()
        return g.score
    } // 33661 Time: 1052ms
}

fun main() = SomeDay.mainify(Day22)