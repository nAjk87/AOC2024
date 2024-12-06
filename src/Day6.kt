import java.io.File

class Day6 {

    init {
        var originalMap = mutableListOf<String>()
        File("Day6.txt").useLines { lines ->
            lines.forEach { line ->
                originalMap.add(line)
            }
        }

        var map = originalMap.toMutableList()

        var done = false

        while (!done) {
            try {
                solve(map)
            } catch (e: Exception) {
                done = true
            }
        }
        var visistedNodes = 0
        map.forEach {
            it.forEach {
                if(it == 'X' || it == '<' || it == '>' || it == '^' || it == 'v') {
                    visistedNodes++
                }
            }
        }

        println(visistedNodes)
        var notsolved = true
        var modifyX = 0
        var modifyY = 0
        var forbiddenCharaters = listOf('^','<','>','v','#')
        var possibleSolutions = 0
        while(notsolved) {
            var lolMap = originalMap.toMutableList()
            if(modifyY == lolMap.size) {
                break
            }
            lolMap[modifyY] = lolMap[modifyY].toCharArray().mapIndexed { index, c ->
                if(index == modifyX && !forbiddenCharaters.contains(c)) {
                    '#'
                } else {
                    c
                }
            }.joinToString("")
            //check if solvable
            var triesLeft = 6000
            while (triesLeft > 0) {
                try {
                    solve(lolMap,triesLeft)
                } catch (e: IndexOutOfBoundsException) {
                    triesLeft = 0
                } catch (e : IllegalArgumentException) {
                    possibleSolutions++
                    triesLeft = 0
                }
                triesLeft--
            }
            if(modifyX > lolMap[modifyY].length -1) {
                modifyX = 0
                modifyY++
            } else if(modifyY > lolMap.size -1) {
                notsolved = false
            } else {
                modifyX++
            }
        }
        println("Day 6 - Part 2 : $possibleSolutions")
    }

    private fun solve(map: MutableList<String>, triesLeft: Int = 3000000) {
        if(triesLeft == 1) {
            throw IllegalArgumentException()
        }
        var yIndex = 0
        map.forEachIndexed { index, s ->
            if (s.contains('^') || s.contains('>') || s.contains('v') || s.contains('<')) {
                yIndex = index
            }
        }
        var xIndex = 0
        var direction = ""
        map[yIndex].forEachIndexed { index, c ->
            if (c == '^') {
                xIndex = index
                direction = "up"
                if (map[yIndex - 1][index] == '#') {
                    direction = "right"
                    if(map[yIndex][index+1] == '#') {
                        direction = "down"
                    }
                }
            } else if (c == '>') {
                xIndex = index
                direction = "right"
                if (map[yIndex][index + 1] == '#') {
                    direction = "down"
                    if(map[yIndex+1][index] == '#') {
                        direction = "left"
                    }
                }

            } else if (c == 'v') {
                xIndex = index
                direction = "down"
                if (map[yIndex + 1][index] == '#') {
                    direction = "left"
                    if(map[yIndex][index-1] == '#') {
                        direction = "up"
                    }
                }

            } else if (c == '<') {
                xIndex = index
                direction = "left"
                if (map[yIndex][index - 1] == '#') {
                    direction = "up"
                    if(map[yIndex-1][index] == '#') {
                        direction = "right"
                    }
                }

            }
        }
        if (direction == "up") {
            map[yIndex] = map[yIndex].replace('^', 'X').replace('<', 'X').replace('>', 'X').replace('v', 'X')
            map[yIndex - 1] = map[yIndex - 1].toCharArray().mapIndexed { index, c ->
                if (index == xIndex) {
                    '^'
                } else {
                    c
                }
            }.joinToString("")
        }
        if (direction == "right") {
            map[yIndex] = map[yIndex].replace('^', 'X').replace('<', 'X').replace('>', 'X').replace('v', 'X')
            map[yIndex] = map[yIndex].toCharArray().mapIndexed { index, c ->
                if (index - 1 == xIndex) {
                    '>'
                } else {
                    c
                }
            }.joinToString("")
        }
        if (direction == "down") {
            map[yIndex] = map[yIndex].replace('^', 'X').replace('<', 'X').replace('>', 'X').replace('v', 'X')
            map[yIndex + 1] = map[yIndex + 1].toCharArray().mapIndexed { index, c ->
                if (index == xIndex) {
                    'v'
                } else {
                    c
                }
            }.joinToString("")
        }
        if (direction == "left") {
            map[yIndex] = map[yIndex].replace('^', 'X').replace('<', 'X').replace('>', 'X').replace('v', 'X')
            map[yIndex] = map[yIndex].toCharArray().mapIndexed { index, c ->
                if (index + 1 == xIndex) {
                    '<'
                } else {
                    c
                }
            }.joinToString("")
        }
    }
}