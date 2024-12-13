import java.io.File

class Day11 {
    init {
        var stones = mutableListOf<Stone>()
        File("Day11.txt").useLines {
            stones = it.toList().first().split(' ').map { Stone(it.toLong(),1) }.toMutableList()
        }

        repeat(75) {
            val newStones = mutableListOf<Stone>()
            stones.forEach { stone ->
                val asString = stone.value.toString()
                if (stone.value == 0L) {
                    newStones.add(stone.copy(value = 1))
                } else if (asString.length % 2 == 0) {
                    newStones.add(Stone(asString.dropLast(asString.length / 2).toLong(),stone.amount))
                    newStones.add(Stone(asString.drop(asString.length / 2).toLong(),stone.amount))
                } else {
                    newStones.add(Stone(stone.value * 2024,stone.amount))
                }
            }
            stones = mutableListOf()
            newStones.forEach { stoney ->
                val indexOfFirst = stones.indexOfFirst { it.value == stoney.value }
                if(indexOfFirst == -1) {
                    stones.add(stoney)
                } else {
                    stones[indexOfFirst].amount += stoney.amount
                }
            }
        }
        var sumOfStones = 0L
        stones.forEach { sumOfStones+=it.amount }
        println(sumOfStones)
    }
    data class Stone(
        val value: Long,
        var amount: Long,
    )
}