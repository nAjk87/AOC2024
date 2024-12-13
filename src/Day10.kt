import java.io.File

class Day10 {

    init {
        val map = mutableListOf<List<Int>>()
        File("Day10.txt").useLines { line ->
            map.addAll(line.toList().map { it.toCharArray().map { it.digitToInt() }.toMutableList() })
        }
        val allZeroes: MutableList<Pair<Int, Int>> = mutableListOf() //X,Y
        map.forEachIndexed { index, line ->
            line.forEachIndexed { index2, int ->
                if (int == 0) {
                    allZeroes.add(index to index2)
                }
            }
        }
        println(allZeroes)
        var pathsFound = 0

        val visitedForThisZero : MutableList<Pair<Int,Int>> = mutableListOf()
        fun checkNeighbours(y: Int, x: Int, currentValue: Int) {
            if (currentValue == 9) {
                pathsFound++
                visitedForThisZero.add(y to x)
            } else {
                try {
                    if (map[y - 1][x] == currentValue + 1) {
                        checkNeighbours(y - 1, x, currentValue + 1)
                    }
                } catch (_: Exception) {
                    println("error")
                }

                try {
                    if (map[y][x + 1] == currentValue + 1) {
                        checkNeighbours(y, x + 1, currentValue + 1)
                    }
                } catch (_: Exception) {
                    println("error")
                }
                try {
                    if (map[y + 1][x] == currentValue + 1) {
                        checkNeighbours(y + 1, x, currentValue + 1)
                    }
                } catch (_: Exception) {
                    println("error")
                }
                try {
                    if (map[y][x - 1] == currentValue + 1) {
                        checkNeighbours(y, x - 1, currentValue + 1)
                    }
                } catch (_: Exception) {
                    println("error")
                }
            }
        }

        //val firstPair = allZeroes.first()

        //checkNeighbours(firstPair.first, firstPair.second, 0)
        //println(visitedForThisZero.distinct().size)
        var sum = 0
        allZeroes.forEach { pair ->
            visitedForThisZero.clear()
            checkNeighbours(pair.first, pair.second, 0)
            sum += visitedForThisZero.distinct().size
        }

        println(sum)
        println(pathsFound)
    }
}