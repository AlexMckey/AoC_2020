import java.io.File

val day = 21
val year = 2020
val inputDir = "d:\\YandexDisk\\DevsExercises\\AdventOfCode\\$year\\PuzzleInput\\"
val filepath = inputDir + "input${day.toString().padStart(2,'0')}.txt"
val data = File(filepath).bufferedReader().readText().trim()

val s = "sqjhc fvjkl (contains soy)\n" +
        "mxmxvkd kfcds sqjhc nhms (contains dairy, fish)\n" +
        "trh fvjkl sbzzf mxmxvkd (contains dairy)\n" +
        "sqjhc mxmxvkd sbzzf (contains fish)"
val r = """((?:\w+\s?)+)\s\(contains ((?:\w+(?:,\s)?)+)\)""".toRegex()
val f1 = data.lines().map {
    val (ingredients, allergens) = r.find(it)!!.destructured
    ingredients.split(" ") to allergens.split(", ")
}
f1
val ings = f1.flatMap { it.first }
ings
val f2 = f1.flatMap { (ings, alls) ->
    alls.map { it to ings }
}
f2
val alls = f2.groupBy({ it.first },{ it.second.toSet() })
    .mapValues { it.value.reduce(Set<String>::intersect) }.toList()
alls
tailrec fun reducted(lst: List<Pair<String,Set<String>>>, acc: List<Pair<String, Set<String>>> = emptyList()): List<Pair<String, Set<String>>> {
    if (lst.isEmpty()) return acc
    val (onces,other) = lst.partition { it.second.size <= 1 }
    return reducted(other.map { it.first to it.second.minus(onces.map{it.second}.reduce(Set<String>::union)) } ,acc + onces)
}
reducted(alls).sortedBy { it.first }.map { it.second }.flatten().joinToString(",")