import java.io.File

class Day3 {

    init {
        var input : String = ""
        File("Day3.txt").useLines { lines ->
            lines.forEach { line ->
                input+= line
            }
        }

        val onlyMul = Regex("""mul\(\d{1,3},\d{1,3}\)""")
        val matches = onlyMul.findAll(input).toList()

        val onlyNumbers = Regex("""\b\d{1,3},\d{1,3}\b""")

        var multiplySum = 0
        matches.forEach { result ->
            val toMul = onlyNumbers.find(result.value)!!.value.split(',')
            multiplySum += toMul[0].toInt() * toMul[1].toInt()
        }
        println("Day3 - Part1 : $multiplySum")

        //val regex = Regex("""mul\(\d{1,3},\d{1,3}\)""")

        //val matches = regex.findAll(text).map { it.value }.toList()

        // part2


        val ok = "do()"
        val faulty = "don't()"

        val splitted = input.split(ok).map {
            it.split(faulty)[0]
        }

        var multiplySum2 = 0
        splitted.forEach { line ->
            val tempMatches = onlyMul.findAll(line).toList()
            tempMatches.forEach { result ->
                val toMul = onlyNumbers.find(result.value)!!.value.split(',')
                multiplySum2 += toMul[0].toInt() * toMul[1].toInt()
            }
        }
        println("Day3 : Part 2 : $multiplySum2")
    }
}