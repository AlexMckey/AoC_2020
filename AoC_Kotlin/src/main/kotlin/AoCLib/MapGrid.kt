package AoCLib

typealias MapGrid<T> = Map<Point,T>

fun List<String>.toMapGrid() = this
    .flatMapIndexed { col, line ->
        line.mapIndexed { row, char ->
            Point(row, col) to char
        }
    }
    .associate { it }

