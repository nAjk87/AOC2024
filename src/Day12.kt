import java.io.File

class Day12 {
    init {
        val garden = mutableListOf<List<Plant>>()
        File("Day12.txt").useLines { lines ->
            lines.forEachIndexed { yIndex, line ->
                val gardenLine = mutableListOf<Plant>()
                line.forEachIndexed { xIndex, char ->
                    gardenLine.add(
                        Plant(
                            type = char,
                            x = xIndex,
                            y = yIndex,
                            false,
                            4,
                            0
                        )
                    )
                }
                garden.add(gardenLine)
            }
        }
        val gardens = mutableListOf<MutableList<Plant>>()
        var gardensIndex = 0
        fun checkPlants(plant: Plant) {
            val y = plant.y
            val x = plant.x
            garden[y][x].visited = true
            if (gardens.getOrNull(gardensIndex) == null) {
                gardens.add(gardensIndex, mutableListOf())
            }
            gardens[gardensIndex].add(plant)
            val left = try {
                garden[y][x - 1]
            } catch (e: Exception) {
                null
            }
            val up = try {
                garden[y - 1][x]
            } catch (e: Exception) {
                null
            }
            val right = try {
                garden[y][x + 1]
            } catch (e: Exception) {
                null
            }
            val down = try {
                garden[y + 1][x]
            } catch (e: Exception) {
                null
            }

            val upLeft = try {
                garden[y - 1][x - 1]
            } catch (e: Exception) {
                null
            }
            val upRight = try {
                garden[y - 1][x + 1]
            } catch (e: Exception) {
                null
            }
            val downRight = try {
                garden[y + 1][x + 1]
            } catch (e: Exception) {
                null
            }
            val downLeft = try {
                garden[y + 1][x - 1]
            } catch (e: Exception) {
                null
            }

            if (left != null && left.type == plant.type) {
                plant.fences--
                if (!left.visited) {
                    checkPlants(left)
                }
            }

            if (up != null && up.type == plant.type) {
                plant.fences--
                if (!up.visited) {
                    checkPlants(up)
                }
            }

            if (right != null && right.type == plant.type) {
                plant.fences--
                if (!right.visited) {
                    checkPlants(right)
                }
            }

            if (down != null && down.type == plant.type) {
                plant.fences--
                if (!down.visited) {
                    checkPlants(down)
                }
            }


            val isLeftFriend = (left != null && left.type == plant.type)
            val isRightFriend = (right != null && right.type == plant.type)
            val isDownFriend = (down != null && down.type == plant.type)
            val isUpFriend = (up != null && up.type == plant.type)
            val isUpLeftFriend = (upLeft != null && upLeft.type == plant.type)
            val isUpRightFriend = (upRight != null && upRight.type == plant.type)
            val isDownRightFriend = (downRight != null && downRight.type == plant.type)
            val isDownLeftFriend = (downLeft != null && downLeft.type == plant.type)

            // add sides according to above analysis
            /*
            *
            *  val adj1 = row - 1 >= 0 && plot[row - 1][col] == currentPlant // top
            val adj2 = row - 1 >= 0 && col + 1 < plot[0].size && plot[row - 1][col + 1] == currentPlant // top right
            val adj3 = col + 1 < plot[0].size && plot[row][col + 1] == currentPlant // right
            val adj4 = row + 1 < plot.size && col + 1 < plot[0].size && plot[row + 1][col + 1] == currentPlant // bottom right
            val adj5 = row + 1 < plot.size && plot[row + 1][col] == currentPlant // bottom
            val adj6 = row + 1 < plot.size && col - 1 >= 0 && plot[row + 1][col - 1] == currentPlant // bottom left
            val adj7 = col - 1 >= 0 && plot[row][col - 1] == currentPlant // left
            val adj8 = row - 1 >= 0 && col - 1 >= 0 && plot[row - 1][col - 1] == currentPlant // top left
            *
            * */

            if ((!isUpFriend && !isRightFriend) || (isUpFriend && !isUpRightFriend && isRightFriend)) {
                plant.sides++
            }

            if ((!isRightFriend && !isDownFriend) || (isRightFriend && !isDownRightFriend && isDownFriend)) {
                plant.sides++
            }

            if ((!isDownFriend && !isLeftFriend) || (isDownFriend && !isDownLeftFriend && isLeftFriend)) {
                plant.sides++
            }

            if ((!isLeftFriend && !isUpFriend) || (isLeftFriend && !isUpLeftFriend && isUpFriend)) {
                plant.sides++
            }
        }
        while (garden.flatten().any { !it.visited }) {
            garden.flatten().forEach { plant ->
                if (!plant.visited) {
                    checkPlants(plant)
                    gardensIndex++
                }
            }
        }


        var sum = 0
        var sides = 0
        gardens.forEach { garden ->
            sum += garden.size * garden.sumOf { it.fences }
            sides += garden.size * garden.sumOf { it.sides }
        }

        println("Day12 - Part 1 : $sum")
        println("Day12 - Part 2 : $sides")
    }

    data class Plant(
        val type: Char,
        val x: Int,
        val y: Int,
        var visited: Boolean,
        var fences: Int,
        var sides: Int,
    )
}