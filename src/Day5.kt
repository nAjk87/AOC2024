import java.io.File
import java.util.*

class Day5 {

    init {
        val rules = mutableMapOf<String, List<String>>()
        val prints = mutableListOf<List<String>>()
        File("Day5.txt").useLines { lines ->
            lines.forEach { line ->
                if (line.contains("|")) {
                    val tempRules = line.split('|')
                    var ruleList = mutableListOf<String>()
                    if (rules[tempRules[0]] != null) {
                        ruleList.addAll(rules[tempRules[0]]!! + tempRules[1])
                    } else {
                        ruleList.add(tempRules[1])
                    }
                    rules[tempRules[0]] = ruleList
                } else if (line.contains(',')) {
                    prints.add(line.split(','))
                }
            }
        }
        var correctPrints = mutableListOf<List<String>>()
        var unCorrectPrints = mutableListOf<MutableList<String>>()
        //lets check
        prints.forEach { print ->
            print.forEachIndexed { index, page ->
                val rules = rules[page]
                if (rules != null) {
                    if (checkIfBreaksRules(rules, print, index)) {
                        unCorrectPrints.add(print.toMutableList())
                        return@forEach
                    }
                }
                if (print.last() == page) {
                    correctPrints.add(print)
                }
            }

        }
        var sumOfMiddle = 0
        correctPrints.forEach { print ->
            sumOfMiddle += print[Math.floorDiv(print.size, 2)].toInt()
        }

        println("Day5 - Part 1 : $sumOfMiddle")


        var myLittleFaultyIndex = 0
        val nowCorrectPrints = mutableListOf<List<String>>()
        while (myLittleFaultyIndex < unCorrectPrints.size) {
            run {
                unCorrectPrints[myLittleFaultyIndex].forEachIndexed { index, page ->
                    val rules = rules[page]
                    if (rules != null) {
                        if (checkIfBreaksRules(rules, unCorrectPrints[myLittleFaultyIndex], index)) {
                            Collections.swap(unCorrectPrints[myLittleFaultyIndex], index, index -1)
                            return@run
                        }
                    }
                }
                nowCorrectPrints.add(unCorrectPrints[myLittleFaultyIndex])
                myLittleFaultyIndex++
            }
        }
        var sumOfNewMiddle = 0
        nowCorrectPrints.forEach { print ->
            sumOfNewMiddle += print[Math.floorDiv(print.size, 2)].toInt()
        }

        println("Day5 - Part 2 : $sumOfNewMiddle")
    }

    fun checkIfBreaksRules(rules: List<String>, print: List<String>, index: Int): Boolean {
        for (n in index - 1 downTo 0) {
            if (rules.contains(print[n])) {
                return true
            }
        }
        return false
    }
}