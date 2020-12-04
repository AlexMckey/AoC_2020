package day04

import InputLib.DayInput

fun checkCountValid(passData: Map<String,String>): Boolean =
    when (passData.count()) {
        8 -> true
        7 -> !passData.containsKey("cid")
        else -> false
    }
fun checkFieldsValid(data: Map<String,String>): Boolean {
    fun checkInRange(v: Int, from: Int, to: Int) = v in from..to
    fun checkHeight(v: String) = when (v.takeLast(2)) {
        "cm" -> checkInRange(v.takeWhile { it.isDigit() }.toInt(),150,193)
        "in" -> checkInRange(v.takeWhile { it.isDigit() }.toInt(),59,76)
        else -> false
    }
    val rules = mapOf<String, (String) -> Boolean>(
            "byr" to {checkInRange(it.toInt(),1920,2002)},
            "iyr" to {checkInRange(it.toInt(),2010,2020)},
            "eyr" to {checkInRange(it.toInt(),2020,2030)},
            "hgt" to {checkHeight(it)},
            "hcl" to {Regex("#[0-9a-f]{6}").matches(it)},
            "ecl" to {Regex("amb|blu|brn|gry|grn|hzl|oth").matches(it)},
            "pid" to {Regex("\\d{9}").matches(it)},
            "cid" to {true}
    )
    return rules.all{ rule -> rule.value(data[rule.key] ?: "0")}
}

fun main() {
    val rawData = DayInput.inputStr("input04.txt")
    val passportData = rawData.split("\n\n")
        .map{ it.replace("\n"," ")}
            .map{ it.split(" ")
                .map{
                        it.split(":").let { list -> list.first() to list.last() } }
                .toMap()
        }
    val res1 = passportData.count { checkCountValid(it) }
    println(res1) // 245
    val res2 = passportData.count { checkFieldsValid(it) }
    println(res2) // 133
}