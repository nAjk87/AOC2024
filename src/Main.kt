import java.io.File
import kotlin.math.abs
import kotlin.system.exitProcess

fun main() {
    println("Hello AOC2024!")
    println("Write 1 for day1 and so on or x for exit")
    while (true) {
        val value = readln()
        if (value == "1") {
            Day1()
        } else if (value == "2") {
            Day2()
        } else if (value == "3") {
            Day3()
        } else if (value == "4") {
            Day4()
        } else if (value == "5") {
            Day5()
        } else if (value == "6") {
            Day6()
        }else if (value == "x") {
            exitProcess(0)
        }
    }
}

