package day08

val s = "nop +0\n" +
        "acc +1\n" +
        "jmp +4\n" +
        "acc +3\n" +
        "jmp -3\n" +
        "acc -99\n" +
        "acc +1\n" +
        "jmp -4\n" +
        "acc +6"
val ps = s.lines()
val r = """(?<op>\w\w\w) (?<param>[+\-]\d+)""".toRegex()
ps.map {
    val (op,param) = r.matchEntire(it)!!.destructured
    op to param.toInt()
}
val gc = AoCLib.GameConsole(s)
gc.runProgram()
gc.acc
gc.state
val p = gc.program
fun changeBug(p:Array<AoCLib.Op>, idx: Int): Array<AoCLib.Op> {
    val newProgram = p.clone()
    newProgram[idx] = if (p[idx] is AoCLib.Nop) AoCLib.Jmp((p[idx] as AoCLib.Nop).param)
    else AoCLib.Nop((p[idx] as AoCLib.Jmp).param)
    return newProgram
}
val idxsBug = p.withIndex().filter {it.value is AoCLib.Nop || it.value is AoCLib.Jmp }.map { it.index }
val pps = idxsBug.map { changeBug(p,it) }
pps.map { it.toList() }
var i = 0
do {
    gc.program = pps[i++]
    gc.runProgram()
} while (gc.state == AoCLib.ConsoleState.Halt)
gc.acc