val s = "abc\n" +
        "\n" +
        "a\n" +
        "b\n" +
        "c\n" +
        "\n" +
        "ab\n" +
        "ac\n" +
        "\n" +
        "a\n" +
        "a\n" +
        "a\n" +
        "a\n" +
        "\n" +
        "b"
val ss = s.split("\n\n")
ss
val res1 = ss.map { it.replace("\n","").toSet().size }.sum()
res1
val r1 = ss.map {
    val cnt = it.count{it == '\n'}+1
    it.replace("\n","")
        .groupingBy { it }.eachCount()
        .count { it.value == cnt }
}.sum()
r1
val r2 = ss.map {
    it.split('\n')
        .map { it.toSet() }
        .reduce{ acc, set -> acc.intersect(set) }
        .count()
}.sum()
r2
