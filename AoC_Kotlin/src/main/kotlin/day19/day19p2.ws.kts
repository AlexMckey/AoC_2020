import AoCLib.splitByBlankLines

val s = "0: 4 1 5\n" +
        "1: 2 3 | 3 2\n" +
        "2: 4 4 | 5 5\n" +
        "3: 4 5 | 5 4\n" +
        "4: \"a\"\n" +
        "5: \"b\"\n" +
        "\n" +
        "ababbb\n" +
        "bababa\n" +
        "abbbab\n" +
        "aaabbb\n" +
        "aaaabbb"
val parts = s.splitByBlankLines()
val rulesStr = parts.first()
val messages = parts.last().lines()
