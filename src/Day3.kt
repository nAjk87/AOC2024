import java.io.File

class Day3 {

    init {
        var input = ""
        File("Day3.txt").useLines { lines ->
            lines.forEach { line ->
                input+= line
            }
        }

        val onlyMulPattern = Regex("""mul\(\d{1,3},\d{1,3}\)""")
        val matches = onlyMulPattern.findAll(input).toList()

        val almostOnlyNumbers = Regex("""\b\d{1,3},\d{1,3}\b""")

        var multiplySum = 0
        matches.forEach { result ->
            val toMul = almostOnlyNumbers.find(result.value)!!.value.split(',')
            multiplySum += toMul[0].toInt() * toMul[1].toInt()
        }
        println("Day3 - Part 1 : $multiplySum")

        val splitted = input.split("do()").map {
            it.split("don't()")[0]
        }

        var multiplySum2 = 0
        splitted.forEach { line ->
            val tempMatches = onlyMulPattern.findAll(line).toList()
            tempMatches.forEach { result ->
                val toMul = almostOnlyNumbers.find(result.value)!!.value.split(',')
                multiplySum2 += toMul[0].toInt() * toMul[1].toInt()
            }
        }
        println("Day3 - Part 2 : $multiplySum2")
    }
}