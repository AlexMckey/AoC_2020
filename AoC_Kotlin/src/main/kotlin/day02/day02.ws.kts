data class Policy(val ch: Char, val fromCount: Int, val toCount: Int) {
    fun checkCountPolicy(password: String): Boolean {
        val passCharCount = password.groupingBy { it }.eachCount()
        return passCharCount.containsKey(ch)
                && passCharCount[ch]!! >= fromCount
                && passCharCount[ch]!! <= toCount
    }
    fun checkPosPolicy(password: String): Boolean {
        val passLen = password.length
        val checkFirstPos = passLen >= fromCount && password[fromCount-1] == ch
        val checkSecondPos = passLen >= toCount && password[toCount-1] == ch
        return checkFirstPos || checkSecondPos
                && !(checkFirstPos && checkSecondPos)
    }
}
val strs = "1-3 a: abcde\n" +
        "1-3 b: cdefg\n" +
        "2-9 c: ccccccccc"
val inputs = strs.lines().map{
    val arr = it.split(": ")
    val policyStr = arr[0].split(" ")
    val counts = policyStr[0].split("-").map { it.toInt() }
    val ch = policyStr[1][0]
    val policy = Policy(ch,counts[0],counts[1])
    policy to arr[1]
}.toMap()
inputs.count {
    it.key.checkCountPolicy(it.value)
}
inputs.count {
    it.key.checkPosPolicy(it.value)
}