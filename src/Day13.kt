import com.google.ortools.linearsolver.MPSolver
import com.google.ortools.linearsolver.MPSolver.ResultStatus
import java.io.File

class Day13 {
    init {

        val AValues = mutableListOf<Pair<Long,Long>>()
        val BValues = mutableListOf<Pair<Long,Long>>()
        val prizes = mutableListOf<Pair<Long,Long>>()

        File("Day13.txt").useLines { lines ->
            lines.forEach { line ->
                if(line.contains('A')) {
                    val values = line.split("A: ")[1].split(", ")
                    AValues.add(values[0].replace("X+","").toLong() to values[1].replace("Y+","").toLong())
                } else if (line.contains('B')) {
                    val values = line.split("B: ")[1].split(", ")
                    BValues.add(values[0].replace("X+","").toLong() to values[1].replace("Y+","").toLong())
                } else if (line.contains("Prize")) {
                    val prize = line.split("X=")[1].split(", ")
                    prizes.add(prize[0].toLong() to prize[1].replace("Y=","").toLong())
                }
            }
        }

        MPSolver.createSolver("SCIP")?.let { solver ->
            // 1. Definiera variabler
            val a = solver.makeIntVar(0.0, Double.POSITIVE_INFINITY, "A")
            val b = solver.makeIntVar(0.0, Double.POSITIVE_INFINITY, "B")

            // 2. Lägg till restriktioner
            // 94A + 34B = 8400
            solver.makeConstraint(8400.0, 8400.0).apply {
                setCoefficient(a, 94.0)
                setCoefficient(b, 34.0)
            }

            // 22A + 67B = 5400
            solver.makeConstraint(5400.0, 5400.0).apply {
                setCoefficient(a, 22.0)
                setCoefficient(b, 67.0)
            }

            // 3. Definiera målfunktionen: Minimize Cost = 3A + B
            val objective = solver.objective()
            objective.setCoefficient(a, 3.0)
            objective.setCoefficient(b, 1.0)
            objective.setMinimization()

            // 4. Lös problemet
            val resultStatus = solver.solve()
            if (resultStatus == ResultStatus.OPTIMAL) {
                println("Optimal lösning hittad:")
                println("A = ${a.solutionValue().toInt()}")
                println("B = ${b.solutionValue().toInt()}")
                println("Minimikostnad = ${objective.value()}")
            } else {
                println("Ingen lösning hittades.")
            }
        } ?: println("Kunde inte skapa solver.")

    }
}