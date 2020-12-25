import AoCLib.splitByBlankLines

val s = "class: 0-1 or 4-19\n" +
        "row: 0-5 or 8-19\n" +
        "seat: 0-13 or 16-19\n" +
        "\n" +
        "your ticket:\n" +
        "11,12,13\n" +
        "\n" +
        "nearby tickets:\n" +
        "3,9,18\n" +
        "15,1,5\n" +
        "5,14,9\n" +
        "3,120,18\n" +
        "15,1,55\n" +
        "20,14,9"
val threePart = s.splitByBlankLines()
val rulesStr = threePart.first()
val ruleR = """(?<field>(?:\w|\s)+): (?<fr1>\d+)-(?<fr2>\d+) or (?<sr1>\d+)-(?<sr2>\d+)""".toRegex(RegexOption.MULTILINE)
val rs = ruleR.findAll(rulesStr)
    //.forEach { println(it.groupValues)}
//    .map{it.groups[1]!!.value to (it.groups[2]!!.value.toInt()..it.groups[3]!!.value.toInt())
//        .union(it.groups[4]!!.value.toInt()..it.groups[5]!!.value.toInt())
//        }
    .map{it.groups[1]!!.value to {x: Int -> (it.groups[2]!!.value.toInt()..it.groups[3]!!.value.toInt())
        .union(it.groups[4]!!.value.toInt()..it.groups[5]!!.value.toInt())
        .contains(x)}}
    .toList()
val tickets = threePart.last().split('\n').drop(1)
    .map { it.split(',').map { it.toInt() } }
tickets
var fields = tickets.flatMap { ticket -> ticket
    .mapIndexed { index, num -> index to rs
        .filter { it.second(num) }
        .map { it.first }.toSet() } }
    .filter { it.second.isNotEmpty() }
    .groupBy({ it.first },{it.second})
    .mapValues { it.value.reduce(Set<String>::intersect) }
fields
var onceFields = fields.filter { it.value.size == 1 }
onceFields
fields = fields.filter { !onceFields.keys.contains(it.key) }.mapValues { onceFields.values.fold(it.value){acc, set -> acc.minus(set) } }
fields
onceFields = onceFields + fields.filter { it.value.size == 1 }
onceFields
fields = fields.filter { !onceFields.keys.contains(it.key) }.mapValues { onceFields.values.fold(it.value){acc, set -> acc.minus(set) } }
fields
onceFields = onceFields + fields.filter { it.value.size == 1 }
onceFields
fields = fields.filter { !onceFields.keys.contains(it.key) }.mapValues { onceFields.values.fold(it.value){acc, set -> acc.minus(set) } }
fields
val fieldsName = onceFields.values.map { it.first() }
fieldsName
val myTicket = threePart.drop(1).first().split('\n').last()
    .split(',').map { it.toInt() }
myTicket.zip(fieldsName)