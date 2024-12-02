import java.io.File
import kotlin.math.abs

class Day1 {
    init {
        val left = mutableListOf<Int>()
        val right = mutableListOf<Int>()

        File("Day1.txt").useLines { lines ->
            lines.forEach { line ->
                val values = line.split("   ")
                left.add(values[0].toInt())
                right.add(values[1].toInt())
            }
        }

        left.sort()
        right.sort()

        var distanceSum = 0
        left.forEachIndexed { index, number ->
            distanceSum += abs(number - right[index])
        }
        println("day1 part1: $distanceSum")

        var similaritySum = 0

        left.forEach { left ->
            var thisLoop = 0
            right.forEach { right ->
                if (right == left) {
                    thisLoop++
                }
            }
            similaritySum += thisLoop * left
        }
        println("day1 part2: $similaritySum")
    }
}