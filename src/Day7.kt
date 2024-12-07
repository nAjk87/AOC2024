import java.io.File

class Day7 {

    init {
        val input = mutableListOf<String>()
        File("Day7.txt").useLines { lines ->
            lines.forEach { line ->
                input.add(line)
            }
        }
        var totalResultPart1 = 0L
        var totalResultPart2 = 0L
        input.forEach { equation ->
            val splitEquation = equation.split(":")
            val result = splitEquation[0].toLong()
            val variables = splitEquation[1].trim().split(" ").map { it.toLong() }
            val permutations = generatePermutations(variables.size - 1)

            //part1
            permutations.forEachIndexed { _, permutation ->
                var sum = variables.first()
                permutation.forEachIndexed { index, operator ->
                    if (operator == "*") {
                        sum = sum.times(variables[index+1])
                    } else if (operator == "+") {
                        sum = sum.plus(variables[index+1])
                    }
                }
                if (sum == result) {
                    totalResultPart1 += result
                    return@forEach
                }
            }
            //part2
            permutations.forEachIndexed { _, permutation ->
                var sum = variables.first()
                permutation.forEachIndexed { index, operator ->
                    if (operator == "*") {
                        sum = sum.times(variables[index+1])
                    } else if (operator == "+") {
                        sum = sum.plus(variables[index+1])
                    } else if (operator == "||") {
                        sum = (sum.toString()+variables[index+1].toString()).toLong()
                    }
                }
                if (sum == result) {
                    totalResultPart2 += result
                    return@forEach
                }
            }
        }
        println("Day 7 Part 1: $totalResultPart1")
        println("Day 7 Part 2: $totalResultPart2")
    }

    fun generatePermutations(amount: Int): MutableList<List<String>> {
        val possibleOperators = listOf("*", "+", "||")

        val permutations = mutableListOf<List<String>>()

        fun recursiveGeneration(index: Int, current: MutableList<String>) {

            //Last one, return it...
            if (index == amount) {
                permutations.add(current.toList()) // Add a copy of the current list
                return
            }

            // Lägg in varje operator på indexet.
            for (operator in possibleOperators) {
                current[index] = operator
                recursiveGeneration(index + 1, current)
            }
        }
        recursiveGeneration(0, MutableList(amount) { "" })

        return permutations
    }
}