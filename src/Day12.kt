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
                            4
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
            if(gardens.getOrNull(gardensIndex) == null) {
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
            } catch(e : Exception) {
                null
            }
            if(left != null && left.type == plant.type) {
                plant.fences--
                if(!left.visited) {
                    checkPlants(left)
                }
            }

            if(up != null && up.type == plant.type) {
                plant.fences--
                if(!up.visited) {
                    checkPlants(up)
                }
            }

            if(right != null && right.type == plant.type) {
                plant.fences--
                if(!right.visited) {
                    checkPlants(right)
                }
            }

            if(down != null && down.type == plant.type) {
                plant.fences--
                if(!down.visited) {
                    checkPlants(down)
                }
            }
        }
        while(garden.flatten().any { !it.visited }) {
            garden.flatten().forEach { plant ->
                if(!plant.visited) {
                    checkPlants(plant)
                    gardensIndex++
                }
            }
        }


        var sum = 0
        gardens.forEach { garden ->
            sum += garden.size * garden.sumOf { it.fences }
        }

        println("Day12 - Part 1 : $sum")
    }

    data class Plant(
        val type: Char,
        val x: Int,
        val y: Int,
        var visited: Boolean,
        var fences: Int,
    )
}