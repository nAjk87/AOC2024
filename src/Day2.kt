import java.io.File
import kotlin.math.abs

class Day2 {
    init {
        val processedInput = mutableListOf<List<Int>>()
        File("Day2.txt").useLines { lines ->
            lines.forEach { line ->
                processedInput.add(line.split(' ').map { it.toInt() })
            }
        }
        var verySafeReports = 0
        var alsoSafeReports = 0
        processedInput.forEachIndexed { index, report ->
            if(checkIfSafeReport(report)) {
                verySafeReports++
            } else {
                for (x in report.indices) {
                    val tempClone = report.filterIndexed { index, _ ->
                        if(index == x) {
                            false
                        } else {
                            true
                        }
                    }
                    if(checkIfSafeReport(tempClone)) {
                        alsoSafeReports++
                        break
                    }
                }
            }
        }
        println("day2 part1: $verySafeReports")
        println("day2 part2: ${verySafeReports + alsoSafeReports}")
    }
    private fun checkIfSafeReport(report: List<Int>) : Boolean {
        if (report.sorted() != report && report.sortedDescending() != report) {
            return false
        } else {
            report.forEachIndexed { index, value ->
                if (index == report.lastIndex) {
                    return true
                }
                val diff = abs(value - report[index + 1])
                if (diff == 0 || diff > 3) {
                    return false
                }
            }
        }
        return true
    }
}