package day21

import AoCLib.SomeDay

typealias Allergens = List<Pair<String,Set<String>>>

object Day21:SomeDay(2020,21) {
    private val r = """((?:\w+\s?)+)\s\(contains ((?:\w+(?:,\s)?)+)\)""".toRegex()

    override fun first(data: String): Any? {
        val foods = data.lines().map {
            val (ingredients, allergens) = r.find(it)!!.destructured
            ingredients.split(" ") to allergens.split(", ")}
        val ings = foods.flatMap { it.first }
        val alls = foods.flatMap { (ings, alls) ->
            alls.map { it to ings }}
            .groupBy({ it.first },{ it.second.toSet() })
            .mapValues { it.value
                .reduce(Set<String>::intersect) }
            .values
            .reduce(Set<String>::union)
        return ings.count { !alls.contains(it) }
    } // 2485 Time: 58ms

    private tailrec fun allergenReduction(lst: Allergens, acc: Allergens = emptyList()): Allergens {
        if (lst.isEmpty()) return acc
        val (onces,other) = lst.partition { it.second.size <= 1 }
        val oncesIngredients = onces.map{ it.second }.reduce(Set<String>::union)
        return allergenReduction(other.map { it.first to it.second.minus(oncesIngredients) } ,acc + onces)
    }

    override fun second(data: String): Any? {
        val foods = data.lines().map {
            val (ingredients, allergens) = r.find(it)!!.destructured
            ingredients.split(" ") to allergens.split(", ")}
        val alls = foods.flatMap { (ings, alls) ->
            alls.map { it to ings }}
            .groupBy({ it.first },{ it.second.toSet() })
            .mapValues { it.value.reduce(Set<String>::intersect) }
            .toList()
        return allergenReduction(alls)
            .sortedBy { it.first }
            .map { it.second }
            .flatten()
            .joinToString(",")
    } // bqkndvb,zmb,bmrmhm,snhrpv,vflms,bqtvr,qzkjrtl,rkkrx
      // Time: 11ms
}

fun main() = SomeDay.mainify(Day21)