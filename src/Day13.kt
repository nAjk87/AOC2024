import java.io.File
import java.util.concurrent.atomic.AtomicLong

class Day13 {
    init {

        val AValues = mutableListOf<Pair<ULong, ULong>>()
        val BValues = mutableListOf<Pair<ULong, ULong>>()
        val prizes = mutableListOf<Pair<ULong, ULong>>()

        File("Day13.txt").useLines { lines ->
            lines.forEach { line ->
                if (line.contains('A')) {
                    val values = line.split("A: ")[1].split(", ")
                    AValues.add(values[0].replace("X+", "").toULong() to values[1].replace("Y+", "").toULong())
                } else if (line.contains('B')) {
                    val values = line.split("B: ")[1].split(", ")
                    BValues.add(values[0].replace("X+", "").toULong() to values[1].replace("Y+", "").toULong())
                } else if (line.contains("Prize")) {
                    val prize = line.split("X=")[1].split(", ")
                    prizes.add((prize[0]).toULong() to ((prize[1].replace("Y=", ""))).toULong())
                }
            }
        }
        val part1 = solve(AValues, BValues, prizes, false)
        val part2 = solve(AValues, BValues, prizes, true)

        println("Part1: $part1")
        println("Part2: $part2")
    }

    data class Claw(
        val Avalues: Pair<ULong, ULong>,
        val BValues: Pair<ULong, ULong>,
        var prize: Pair<ULong, ULong>
    )

    private fun solve(
        Avalues: List<Pair<ULong, ULong>>,
        BValues: List<Pair<ULong, ULong>>,
        prizes: List<Pair<ULong, ULong>>,
        addExtraInBeginning: Boolean = false
    ): Long {
        val sum = AtomicLong(0)

        val clawList = mutableListOf<Claw>()

        Avalues.forEachIndexed { index, pair ->
            clawList.add(Claw(pair, BValues[index], prizes[index]))
        }

        clawList
            .parallelStream()
            .forEach { claw ->
                if (addExtraInBeginning) {
                    claw.prize = claw.prize.first + 10000000000000uL to claw.prize.second + 10000000000000uL
                }
                val solution = solveLinearEquation(
                    claw.Avalues.first.toDouble(),
                    claw.BValues.first.toDouble(),
                    claw.Avalues.second.toDouble(),
                    claw.BValues.second.toDouble(),
                    claw.prize.first.toDouble(),
                    claw.prize.second.toDouble(),
                )

                if (solution.first % 1.0 == 0.0 && solution.second % 1.0 == 0.0)
                    sum.addAndGet((solution.first.toULong() * 3uL + solution.second.toULong()).toLong())
            }

        return sum.get()
    }

    private fun solveLinearEquation(
        aX: Double,
        bX: Double,
        aY: Double,
        bY: Double,
        prizeX: Double,
        prizeY: Double
    ): Pair<Double, Double> {
        val det = (aX * bY - aY * bX)
        if (det == 0.0)
            throw Exception("går ej att lösa")

        val x = (prizeX * bY - prizeY * bX) / det
        val y = (prizeY * aX - prizeX * aY) / det

        return Pair(x, y)
    }
}