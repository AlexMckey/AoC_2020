package AoCLib

fun Char.toIntValue(): Int = toInt() - 48
fun Char.toAlphabetIndex(): Int = toLowerCase() - 'a'