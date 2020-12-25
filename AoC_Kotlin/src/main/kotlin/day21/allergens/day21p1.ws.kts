val s = "mxmxvkd kfcds sqjhc nhms (contains dairy, fish)\n" +
        "sqjhc fvjkl (contains soy)\n" +
        "trh fvjkl sbzzf mxmxvkd (contains dairy)\n" +
        "sqjhc mxmxvkd sbzzf (contains fish)"
val r = """((?:\w+\s?)+)\s\(contains ((?:\w+(?:,\s)?)+)\)""".toRegex()
val f1 = s.lines().map {
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
    .mapValues { it.value.reduce(Set<String>::intersect) }
    .values.reduce(Set<String>::union)
alls
ings.count { !alls.contains(it) }