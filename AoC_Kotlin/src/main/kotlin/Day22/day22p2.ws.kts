import AoCLib.splitByBlankLines
import Day22.Day22

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
data class Game(val p1: Deck, val p2: Deck, val isRecursiveCombat: Boolean = false) {
    private val cache = mutableListOf<Int>()
    private var round: Int = 1
    private val game: Int = gN
    var winner: Int = 0
        private set
    var winnerDeck: Deck? = null
        private set
    val score: Long get() = winnerDeck?.score ?: 0L
    private fun calcHash(d1: Deck, d2: Deck): Int =
        "${d1.cards.hashCode()}:${d2.cards.hashCode()}".hashCode()
    private fun whoWin(p1card: Int, p2card: Int): Int =
        if (!isRecursiveCombat)
            if (p1card > p2card) 1 else 2
        else {
            if (p1.cards.size >= p1card && p2.cards.size >= p2card) {
                println("Recursive combat")
                val inngerGame = Game(
                    p1.copy(cards = p1.cards.take(p1card).toMutableList()),
                    p2.copy(cards = p2.cards.take(p2card).toMutableList()),
                    true)
                gN++
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
        val sb = StringBuilder()
        sb.append("g:$game(r:$round) = ")
        sb.append(p1.cards.reversed())
        sb.append(": ")
        sb.append(p1card)
        sb.append(if (winner == 1)" <| " else " |> ")
        sb.append(p2card)
        sb.append(" :")
        sb.append(p2.cards)
        println(sb.toString())
        roundWinner.cards.addAll(roundWinner.cards.size, roundCards)
//        sb.clear()
//        sb.append("            ")
//        sb.append(p1.cards.reversed())
//        sb.append(" <|> ")
//        sb.append(p2.cards)
//        println(sb.toString())
//        sb.clear()
    }
    fun runGame() {
        gN++
        while (p1.cards.isNotEmpty() && p2.cards.isNotEmpty()) {
            nextRound()
            round++
        }
        winner = if (p1.cards.isEmpty()) 2 else 1
        winnerDeck = if (p1.cards.isEmpty()) p2 else p1
        gN--
    }
    companion object {
        private var gN: Int = 1
        fun parseDeck(s: String): Deck {
            val parts = s.lines()
            val name = parts.first().dropLast(1)
            val cards = parts.drop(1).map { it.toInt() }
            return Deck(name, cards.toMutableList())
        }
    }
}
val p1rec = Deck("p1rec", mutableListOf(43,19))
val p2rec = Deck("p2rec", mutableListOf(2,29,14))
val gSampleRec = Game(p1rec,p2rec,true)
gSampleRec.runGame()
gSampleRec.winnerDeck?.score
val p1 = parseDeck(parts.first())
p1
val p2 = parseDeck(parts.last())
p2
val gRec = Game(p1,p2,true)
gRec.runGame()
gRec.winner
gRec.winnerDeck
gRec.score