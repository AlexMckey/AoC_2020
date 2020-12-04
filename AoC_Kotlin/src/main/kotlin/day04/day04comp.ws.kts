val s = "byr:1957 pid:177cm ecl:blu eyr:2020 hcl:#cfa07d\n" +
        "iyr:2010 hgt:183cm"
val r = """eyr:(?<eyr>\d{4})|cid:(?<cid>\d+)|byr:(?<byr>\d{4})|iyr:(?<iyr>\d{4})|hgt:(?<hgt>\d{3}cm|\d{2}in)|pid:(?<pid>[0-9]{9})|hcl:(?<hcl>#[0-9a-f]{6})|ecl:(?<ecl>amb|blu|brn|gry|grn|hzl|oth)""".toRegex(RegexOption.MULTILINE)
val passFields = listOf("byr","iyr","eyr","hgt","hcl","ecl","pid","cid")
val d = r.findAll(s.replace(" ","\n")).map {
    val arr = it.value.split(":")
    arr.first() to arr.last()
}.toMap()
d
fun validateFields(data: Map<String,String>): Map<String,Boolean> {
    fun checkInRange(v: Int, from: Int, to: Int) = v in from..to
    fun checkHeight(v: String) = when (v.takeLast(2)) {
        "cm" -> checkInRange(v.take(3).toInt(),150,193)
        "in" -> checkInRange(v.take(2).toInt(),59,76)
        else -> false
    }
    return passFields.map {
        val value = data[it]
        if (value == null) it to false
        else when (it) {
            "byr" -> it to checkInRange(value.toInt(),1920,2002)
            "iyr" -> it to checkInRange(value.toInt(),2010,2020)
            "eyr" -> it to checkInRange(value.toInt(),2020,2030)
            "hgt" -> it to checkHeight(value)
            "hcl","ecl","pid","cid" -> it to true
            else -> it to false
        }
    }.toMap()
}
validateFields(d)