val rs = """eyr:(?<eyr>\d{4})|cid:(?<cid>\d+)|byr:(?<byr>\d{4})|iyr:(?<iyr>\d{4})|hgt:(?<hgt>\d{3}cm|\d{2}in)|pid:(?<pid>\d{9})|hcl:(?<hcl>#[0-9a-f]{6})|ecl:(?<ecl>amb|blu|brn|gry|grn|hzl|oth)"""
val r = Regex(rs,RegexOption.MULTILINE)
val s = "pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980\n" +
        "hcl:#623a2f\n\n" +
        "eyr:2029 ecl:blu cid:129 byr:1989\n" +
        "iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm\n\n" +
        "hcl:#888785\n" +
        "hgt:164cm byr:2001 iyr:2015 cid:88\n" +
        "pid:545766238 ecl:hzl\n" +
        "eyr:2022\n\n" +
        "iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719\n\n" +
        "eyr:1972 cid:100\n" +
        "hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926\n\n" +
        "iyr:2019\n" +
        "hcl:#602927 eyr:1967 hgt:170cm\n" +
        "ecl:grn pid:012533040 byr:1946\n\n" +
        "hcl:dab227 iyr:2012\n" +
        "ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277\n\n" +
        "hgt:59cm ecl:zzz\n" +
        "eyr:2038 hcl:74454a iyr:2023\n" +
        "pid:3556412378 byr:2007"
val passportData = s.split("\n\n")
    .map{
        r.findAll(it.replace(" ","\n")).map {
            val arr = it.value.split(":")
            arr.first() to arr.last()
        }.toMap()
    }
passportData
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
//passportData.map { isValid(it) }
val p = passportData[0]
150 in 150..193
193 in 150..193
val passFields = listOf("byr","iyr","eyr","hgt","hcl","ecl","pid","cid")

validateFields(p).filterNot { it.key == "cid" }.all { it.value }