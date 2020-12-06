package AoCLib

object InputTransform {
    fun String.toInts(): List<Int> = this.lines().map { it.toInt() }
    fun String.toStrs(): List<String> = this.lines()
    fun String.splitByBlankLines(): List<String> = this.split("\n\n")
    fun String.replaceAll(pairs: List<Pair<Char,Char>>) = pairs.fold(this){ acc, (from, to) -> acc.replace(from,to) }
}