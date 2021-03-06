package day08.bootgame

import AoCLib.*
import AoCLib.CPU
import AoCLib.ConsoleState
import AoCLib.Op
import AoCLib.Nop
import AoCLib.Jmp

object Day08: SomeDay(2020,8) {
    override val title = "Handheld Halting"

    override fun first(data: String): Any? {
        val console = CPU(data)
        console.runProgram()
        return console.acc
    } // 1317 Time: 35ms

    private fun changeBug(p:Array<Op>, idx: Int): Array<Op> {
        val newProgram = p.clone()
        newProgram[idx] = if (p[idx] is Nop) Jmp((p[idx] as Nop).param)
        else Nop((p[idx] as Jmp).param)
        return newProgram
    }
    override fun second(data: String): Any? {
        val console = CPU(data)
        val program = console.program
        val programs = program.withIndex().filter {it.value is Nop || it.value is Jmp }
            .map { it.index }.map { changeBug(program,it) }
        var i = 0
        do {
            console.program = programs[i++]
            console.runProgram()
        } while (console.state == ConsoleState.Halt)
        return console.acc
    } // 1033 Time: 21ms
}

fun main() = SomeDay.mainify(Day08)