import java.io.File
import java.util.*

class Day9 {

    init {
        var input = ""
        File("Day9.txt").useLines {
            input = it.toList().first()
        }

        var id = 0
        var fileMap = mutableListOf<String>()
        input.forEachIndexed { index, c ->
            val digit = c.digitToInt()
            //This is a file
            if (index % 2 == 0) {
                repeat(digit) {
                    fileMap.add(id.toString())
                }
                id++
            } else {
                repeat(digit) { fileMap.add(".") }
            }
        }
        //index to Add 2
        //index to remove
        var solving = true
        var fileMap2 = fileMap.toMutableList()
        while (solving) {
            var freeIndex = 0
            var moveIndex = 0
            for (index in 0..fileMap.size - 1) {
                val leftChar = fileMap[index]
                if (leftChar == ".") {
                    freeIndex = index
                    for (innerIndex in fileMap.size - 1 downTo 0) {
                        val rightChar = fileMap[innerIndex]
                        if (rightChar != ".") {
                            moveIndex = innerIndex
                            break
                        }
                    }
                    break
                }
            }
            if (freeIndex > moveIndex) {
                solving = false
            } else {
                Collections.swap(fileMap, moveIndex, freeIndex)
            }

        }
        var sum = 0L
        fileMap.forEachIndexed { index, c ->
            if (c != ".") {
                sum += index * c.toLong()
            }
        }
        println("Day 9 - Part 1 : $sum")


        //Part 2, lets move chunks


        // 00...111...2...333.44.5555.6666.777.888899
        var currentSymbol = "0"
        val memoryChunkList = mutableListOf<MemoryChunk>()
        var size = 0
        fileMap2.forEachIndexed { index, c ->
            if (c == currentSymbol && index == fileMap2.size - 1) {
                size++
                memoryChunkList.add(MemoryChunk(currentSymbol != ".", currentSymbol, size))
            } else if (c == currentSymbol) {
                size++
            } else {
                memoryChunkList.add(MemoryChunk(currentSymbol != ".", currentSymbol, size))
                currentSymbol = c
                size = 1
            }

        }

        while (memoryChunkList.any { it.isFile && !it.handled }) {
            val chunkToMoveIndex = memoryChunkList.indexOfLast { !it.handled && it.isFile }
            val chunkToMove = memoryChunkList[chunkToMoveIndex]
            chunkToMove.handled = true
            val firstFreeSpaceIndex = memoryChunkList.indexOfFirst { !it.isFile && it.size >= chunkToMove.size }
            if (firstFreeSpaceIndex != -1 && chunkToMoveIndex > firstFreeSpaceIndex) {
                val firstFreeSpace = memoryChunkList[firstFreeSpaceIndex]
                val memoryLeft = firstFreeSpace.size - chunkToMove.size

                memoryChunkList.remove(chunkToMove)
                memoryChunkList.remove(firstFreeSpace)
                memoryChunkList.add(firstFreeSpaceIndex, chunkToMove)
                memoryChunkList.add(chunkToMoveIndex, MemoryChunk(false, ".", chunkToMove.size))
                if (memoryLeft > 0) {
                    memoryChunkList.add(firstFreeSpaceIndex + 1, MemoryChunk(false, ".", memoryLeft))
                }
            }
        }
        //compact it
        var compacting = true
        while (compacting) {
            run {
                memoryChunkList.forEachIndexed { index, c ->
                    if (index != memoryChunkList.size - 1) {
                        val next = memoryChunkList[index + 1]
                        if (!c.isFile && !next.isFile) {
                            memoryChunkList[index].size += next.size
                            memoryChunkList.removeAt(index + 1)
                            return@run
                        }
                    }
                }
            }
            val lol = memoryChunkList.mapIndexed { index, c ->
                if (index == memoryChunkList.size - 1) {
                    false
                } else if (!c.isFile && !memoryChunkList[index + 1].isFile) {
                    true
                } else {
                    false
                }
            }
            if (lol.all { !it }) {
                compacting = false
            }
        }
        var sum2 = 0L
        var sumIndex = 0
        memoryChunkList.forEachIndexed { index, memoryChunk ->
            if(memoryChunk.symbol == ".") {
                sumIndex += memoryChunk.size
            } else {
                repeat(memoryChunk.size) {
                    sum2 += memoryChunk.symbol.toLong() * sumIndex
                    sumIndex+= 1
                }
            }
        }

        println("Day 9 - Part2 : $sum2")
    }
    // Gå baklänges och hitta nästa sak vi ska flytta
    // Gå framlänges och hitta om det finns en plats där vi skall lägga det.
    // Kanske göra objekt här? Typ type = file/free, space = 1-90213, id = ?

}

data class MemoryChunk(
    val isFile: Boolean,
    val symbol: String,
    var size: Int,
    var handled: Boolean = false,
)
