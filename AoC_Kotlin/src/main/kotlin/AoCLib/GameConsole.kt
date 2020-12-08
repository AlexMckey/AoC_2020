package AoCLib

data class CPUState(val ip: Int = 0, val acc: Int = 0) {
    fun step(): CPUState = this.copy(ip = ip+1)
    fun changeAcc(delta: Int): CPUState = this.copy(ip = ip+1, acc = acc+delta)
    fun changeIp(delta: Int): CPUState = this.copy(ip = ip+delta)
}

enum class ConsoleState {
    Running, Stop, Halt, InputSuspend
}

abstract class Op{
    abstract fun Do(state: CPUState): CPUState
}
data class Nop(val param: Int = 0): Op() {
    override fun Do(state: CPUState): CPUState = state.step()
}
data class Acc(val param: Int = 0): Op() {
    override fun Do(state: CPUState): CPUState = state.changeAcc(param)
}
data class Jmp(val param: Int = 1): Op() {
    override fun Do(state: CPUState): CPUState = state.changeIp(param)
}

val opRegex = """(?<op>\w\w\w) (?<param>(?:\+|-)\d+)""".toRegex()

class GameConsole(programText: String) {
    private var cpuState: CPUState = CPUState()
    private var consoleState: ConsoleState = ConsoleState.Stop
    private var step = 0
    private var memory: Array<Op> = emptyArray()
    val acc: Int
        get() = cpuState.acc
    var program: Array<Op>
        get() = memory.clone()
        set(value) {
            memory = value
            reset()
        }
    val state: ConsoleState
        get() = consoleState
    init {
        memory = parseProgram(programText)
    }
    private fun reset() {
        cpuState = CPUState()
        consoleState = ConsoleState.Stop
        step = 0
    }
    private fun doOneStep() {
        cpuState = memory[cpuState.ip].Do(cpuState)
        cpuState.changeIp(1)
    }
    fun runProgram() {
        val ipExecuted = mutableSetOf<Int>()
        consoleState = ConsoleState.Running
        while (consoleState == ConsoleState.Running) {
            ipExecuted.add(cpuState.ip)
            doOneStep()
            if (ipExecuted.contains(cpuState.ip)) consoleState = ConsoleState.Halt
            if (cpuState.ip >= memory.size) consoleState = ConsoleState.Stop
            step++
        }
    }
    companion object {
        fun parseProgram(programText: String): Array<Op> =
            programText.lines().map {
                val (op,param) = opRegex.matchEntire(it)!!.destructured
                when (op) {
                    "nop" -> Nop()
                    "acc" -> Acc(param.toInt())
                    "jmp" -> Jmp(param.toInt())
                    else -> Nop()
                }}.toTypedArray()
    }
}