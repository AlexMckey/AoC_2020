import AoCLib.splitByBlankLines

val s = "Player 1:\n" +
        "9\n" +
        "2\n" +
        "6\n" +
        "3\n" +
        "1\n" +
        "\n" +
        "Player 2:\n" +
        "5\n" +
        "8\n" +
        "4\n" +
        "7\n" +
        "10"
val parts = s.splitByBlankLines()
parts
data class Deck(val player: String, val cards: MutableList<Int>) {
    val score get() = cards.reversed().withIndex().sumOf{ it.value * (it.index+1L) }
}
fun parseDeck(s: String): Deck {
    val parts = s.lines()
    val name = parts.first().dropLast(1)
    val cards = parts.drop(1).map { it.toInt() }
    return Deck(name,cards.toMutableList())
}
val p1 = parseDeck(parts.first())
p1.cards
val p2 = parseDeck(parts.last())
p2.cards
data class Game(val p1: Deck, val p2: Deck) {
    private var round: Int = 0
    var winner: Deck? = null
        private set
    val score: Long get() = winner?.score ?: 0L
    val rounds: Int get() = round
    fun nextRound() {
        val p1card = p1.cards.removeAt(0)
        val p2card = p2.cards.removeAt(0)
        val (roundWinner, roundCards) =
            if (p1card > p2card)
                p1 to listOf(p1card,p2card)
            else p2 to listOf(p2card,p1card)
        roundWinner.cards.addAll(roundWinner.cards.size, roundCards)
    }
    fun runGame() {
        while (p1.cards.isNotEmpty() && p2.cards.isNotEmpty()) {
            nextRound()
            round++
        }
        winner = if (p1.cards.isEmpty()) p2 else p1
    }
}
val g = Game(p1,p2)
g.runGame()
g.score
g.winner