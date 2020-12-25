import java.util.Stack

val rs = """[()\d+*]""".toRegex()

val s = "1 + 2 * 3 + 4 * 5 + 6"
val sa = rs.findAll(s).map { it.groupValues }.toList()
sa

val OPS = "+*"

fun infixToPostfix(infix: String): String {
    val sb = StringBuilder()
    val s = Stack<Int>()
    val rx = Regex("""\s""")
    for (token in infix.split(rx)) {
        if (token.isEmpty()) continue
        val c = token[0]
        val idx = OPS.indexOf(c)

        // check for operator
        if (idx != - 1) {
            if (s.isEmpty()) {
                s.push(idx)
            }
            else {
                while (!s.isEmpty()) {
                    val prec2 = s.peek() / 2
                    val prec1 = idx / 2
                    if (prec2 > prec1 || (prec2 == prec1 && c != '^')) {
                        sb.append(OPS[s.pop()]).append(' ')
                    }
                    else break
                }
                s.push(idx)
            }
        }
        else if (c == '(') {
            s.push(-2)  // -2 stands for '('
        }
        else if (c == ')') {
            // until '(' on stack, pop operators.
            while (s.peek() != -2) sb.append(OPS[s.pop()]).append(' ')
            s.pop()
        }
        else {
            sb.append(token).append(' ')
        }
    }
    while (!s.isEmpty()) sb.append(OPS[s.pop()]).append(' ')
    return sb.toString()
}
fun transl(s: String): List<String> {
    val res = mutableListOf<String>()
    val st = Stack<String>()
    rs.findAll(s).forEach {
        when (it.value) {
            "(" -> st.push("(")
            ")" -> {
                while (st.peek() != "(")
                    res.add(st.pop())
                st.pop()
            }
            "*","+" -> {
                while (st.isNotEmpty() && st.peek() != "(")
                    res.add(st.pop())
                st.push(it.value)
            }
            else -> res.add(it.value)
        }
    }
    while (st.isNotEmpty())
        res.add(st.pop())
    return res
}
val ss = transl(s)
ss
val ss_ = infixToPostfix(s)
ss_
ss_.trim().split(' ')
val s1 = "7 * (7 * 6 + 7 * 5 + (9 + 2 * 2 * 4)) + 7 * 3 * 5"
val ss1 = transl(s1)
ss1
//val ss1_ = infixToPostfix(s1)
//ss1_
//ss1_.trim().split(' ')
fun calc(s: String): Int {
    var ts = s.trim().split(' ')
    val acc = Stack<Int>()
    while (ts.isNotEmpty()){
        val op = ts.first()
        acc.push(when (op) {
            "+" -> acc.pop() + acc.pop()
            "*" -> acc.pop() * acc.pop()
            else -> op.toInt()
        })
        ts = ts.drop(1)
    }
    return acc.pop()
}
calc(ss_)
calc(ss.joinToString(" "))
calc(ss1.joinToString(" "))