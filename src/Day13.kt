import org.chocosolver.solver.Model
import org.chocosolver.solver.variables.IntVar

import java.io.File

class Day13 {
    init {

        val AValues = mutableListOf<Pair<Int,Int>>()
        val BValues = mutableListOf<Pair<Int,Int>>()
        val prizes = mutableListOf<Pair<Int,Int>>()

        File("Day13.txt").useLines { lines ->
            lines.forEach { line ->
                if(line.contains('A')) {
                    val values = line.split("A: ")[1].split(", ")
                    AValues.add(values[0].replace("X+","").toInt() to values[1].replace("Y+","").toInt())
                } else if (line.contains('B')) {
                    val values = line.split("B: ")[1].split(", ")
                    BValues.add(values[0].replace("X+","").toInt() to values[1].replace("Y+","").toInt())
                } else if (line.contains("Prize")) {
                    val prize = line.split("X=")[1].split(", ")
                    prizes.add(("10000000000000"+prize[0]).toInt() to ("10000000000000"+(prize[1].replace("Y=",""))).toInt())
                }
            }
        }
        var index = 0
        var sum = 0
        while(index < prizes.size) {
            val tempCost = lPSolve(AValues[index], BValues[index], prizes[index])
            if(tempCost != -1) {
                sum += tempCost
            }
            index++
        }
        println(sum)
    }

    private fun lPSolve(Avalues : Pair<Int,Int>, BValues : Pair<Int,Int>, target: Pair<Int,Int>) : Int {
        val model = Model("LP SOLVER")

        // Variabler
        val A: IntVar = model.intVar("A", 0, 1000) // Begränsning av domän
        val B: IntVar = model.intVar("B", 0, 1000)

        // Restriktioner
        // 94A + 22B = 8400
        model.scalar(arrayOf(A, B), intArrayOf(Avalues.first, BValues.first), "=", target.first).post()

        // 34A + 67B = 5400
        model.scalar(arrayOf(A, B), intArrayOf(Avalues.second, BValues.second), "=", target.second).post()

        // Målfunktion: Minimize Cost = 3A + B
        val maxCost = 3 * 1000 + 1000 // Maxvärde baserat på A och B
        val cost = model.intVar("Cost", 0, maxCost) // Begränsad domän
        model.scalar(arrayOf(A, B), intArrayOf(3, 1), "=", cost).post()
        model.setObjective(Model.MINIMIZE, cost)

        // Lös problemet
        val solver = model.solver
        if (solver.solve()) {
            return cost.value
        } else {
            return -1
        }
    }
}