package day11.seats

import AoCLib.*

object Day11: SomeDay(2020,11) {
    override val title = "Seating System"

    private fun List<String>.convertToSeats() = this
        .toMapGrid()
        .filterValues { it != '.' }
    private fun printSeats(xRange: IntRange, yRange: IntRange, seats: Map<Point,Char>) {
        for (y in yRange) {
            for (x in xRange)
                print(seats.getOrDefault(Point(x, y), '.'))
            println()
        }
    }

    override fun first(data: String): Any? {
        var seats = data.toStrs().convertToSeats()
        var changesCnt: Int
        do {
            changesCnt = 0
            val newSeats = seats
                .mapValues { kvp -> when {
                    kvp.value == 'L' &&
                            kvp.key.near8().mapNotNull { seats[it] }
                                .all { it == 'L' } -> {
                        changesCnt++
                        '#'
                    }
                    kvp.value == '#' &&
                            kvp.key.near8().mapNotNull { seats[it] }
                                .count { it == '#' } >= 4 -> {
                        changesCnt++
                        'L'
                    }
                    else -> kvp.value
                } }
            seats = newSeats
        } while (changesCnt > 0)
        return seats.count { it.value == '#' }
    } // 2211 Time: 428ms

    fun near8Dirs(curPoint: Point, seats: Map<Point,Char>, bounds: Pair<IntRange,IntRange>): List<Point?> =
        listOf(Point.toL, Point.toR, Point.toU, Point.toD, Point.toUL, Point.toUR, Point.toDL, Point.toDR)
            .map { curPoint.toDirPoints(it)
                .find { seats.containsKey(it) ||
                        !it.inBounds(bounds.first,bounds.second) } }



    override fun second(data: String): Any? {
        var seats = data.toStrs().convertToSeats()
        val bounds = data.toStrs().bounds()
        var changesCnt: Int
        do {
            changesCnt = 0
            val newSeats = seats
                .mapValues { kvp -> when {
                    kvp.value == 'L' &&
                            near8Dirs(kvp.key,seats,bounds).mapNotNull { seats[it] }
                                .all { it == 'L' } -> {
                        changesCnt++
                        '#'
                    }
                    kvp.value == '#' &&
                            near8Dirs(kvp.key,seats,bounds).mapNotNull { seats[it] }
                                .count { it == '#' } >= 5 -> {
                        changesCnt++
                        'L'
                    }
                    else -> kvp.value
                } }
            seats = newSeats
        } while (changesCnt > 0)
        return seats.count { it.value == '#' }
    }
}

fun main() = SomeDay.mainify(Day11)