import java.io.File

class Day4 {

    init {
        var xmaslist = mutableListOf<String>()
        File("Day4.txt").useLines { lines ->
            lines.forEach { line ->
                xmaslist.add(line)
            }
        }
        println(xmaslist)
        var xMassesFound = 0
        xmaslist.forEachIndexed { x, row ->
            row.forEachIndexed { y, value ->
                if (value == 'X') {
                    //UPLEFT
                    if (check(x - 1, y - 1, 'M', xmaslist)) {
                        if (check(x - 2, y - 2, 'A', xmaslist)) {
                            if (check(x - 3, y - 3, 'S', xmaslist)) {
                                xMassesFound++
                            }
                        }
                    }
                    //UP
                    if (check(x, y - 1, 'M', xmaslist)) {
                        if (check(x, y - 2, 'A', xmaslist)) {
                            if (check(x, y - 3, 'S', xmaslist)) {
                                xMassesFound++
                            }
                        }
                    }
                    //UPRIGHT
                    if (check(x + 1, y - 1, 'M', xmaslist)) {
                        if (check(x + 2, y - 2, 'A', xmaslist)) {
                            if (check(x + 3, y - 3, 'S', xmaslist)) {
                                xMassesFound++
                            }
                        }
                    }
                    //RIGHT
                    if (check(x + 1, y, 'M', xmaslist)) {
                        if (check(x + 2, y, 'A', xmaslist)) {
                            if (check(x + 3, y, 'S', xmaslist)) {
                                xMassesFound++
                            }
                        }
                    }
                    //DOWNRIGHT
                    if (check(x + 1, y + 1, 'M', xmaslist)) {
                        if (check(x + 2, y + 2, 'A', xmaslist)) {
                            if (check(x + 3, y + 3, 'S', xmaslist)) {
                                xMassesFound++
                            }
                        }
                    }
                    //DOWN
                    if (check(x, y + 1, 'M', xmaslist)) {
                        if (check(x, y + 2, 'A', xmaslist)) {
                            if (check(x, y + 3, 'S', xmaslist)) {
                                xMassesFound++
                            }
                        }
                    }
                    //DOWNLEFT
                    if (check(x - 1, y + 1, 'M', xmaslist)) {
                        if (check(x - 2, y + 2, 'A', xmaslist)) {
                            if (check(x - 3, y + 3, 'S', xmaslist)) {
                                xMassesFound++
                            }
                        }
                    }
                    //LEFT
                    if (check(x - 1, y, 'M', xmaslist)) {
                        if (check(x - 2, y, 'A', xmaslist)) {
                            if (check(x - 3, y, 'S', xmaslist)) {
                                xMassesFound++
                            }
                        }
                    }
                }
            }
        }
        println("Day4 - Part1 : $xMassesFound")

        var xMasCrossFound = 0
        xmaslist.forEachIndexed { x, row ->
            row.forEachIndexed { y, value ->
                if (value == 'A') {
                    var halfCrossfound = false
                    if (check(x - 1, y - 1, 'M', xmaslist)) {
                        if (check(x + 1, y + 1, 'S', xmaslist)) {
                            halfCrossfound = true
                        }
                    } else if (check(x - 1, y - 1, 'S', xmaslist)) {
                        if (check(x + 1, y + 1, 'M', xmaslist)) {
                            halfCrossfound = true
                        }
                    }

                    if(halfCrossfound) {
                        if (check(x + 1, y - 1, 'M', xmaslist)) {
                            if (check(x -1, y + 1, 'S', xmaslist)) {
                                xMasCrossFound++
                            }
                        } else if (check(x + 1, y - 1, 'S', xmaslist)) {
                            if (check(x -1, y + 1, 'M', xmaslist)) {
                                xMasCrossFound++
                            }
                        }
                    }
                }
            }
        }
        println("Day4 - Part2 : $xMasCrossFound")
    }

    fun check(x: Int, y: Int, char: Char, xmaslist: List<String>): Boolean {
        return try {
            if (xmaslist[x][y] == char) {
                true
            } else false
        } catch (e: Exception) {
            false
        }
    }
}