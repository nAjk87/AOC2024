import java.io.File

class Day8 {
    init {
        val nodes = mutableListOf<Node>()
        File("Day8.txt").useLines { lines ->
            lines.forEachIndexed { yIndex, line ->
                line.forEachIndexed { xIndex, char ->
                    val antiNodoes = if(char != '.') {
                        mutableListOf(char)
                    } else {
                        mutableListOf()
                    }
                    nodes.add(
                        Node(
                            antennaSign = char,
                            x = xIndex,
                            y = yIndex,
                            antiNodoes
                        )
                    )
                }
            }
        }
        val nodesBySign = nodes.groupBy { it.antennaSign }.filter { it.key != '.' }.values

        val maxXIndex = nodes.maxBy { it.x }.x
        val maxYIndex = nodes.maxBy { it.y }.y

        nodesBySign.forEach { signs ->

            //nu går vi per symbol
            signs.forEach { node ->
                val myNeighbours = node.getMyNeighbours(signs)

                //calc antinodes

                myNeighbours.forEach { neighbour ->
                    // distance in X
                    var lol = true
                    var xFirst = node.x
                    var xSecond = neighbour.x
                    var yFirst = node.y
                    var ySecond = neighbour.y
                    while (lol) {

                        if (node.x >= neighbour.x) {
                            xFirst +=  Math.abs(neighbour.x - node.x)
                            xSecond += -Math.abs(neighbour.x - node.x)
                        } else {
                            xFirst += -Math.abs(neighbour.x - node.x)
                            xSecond += Math.abs(neighbour.x - node.x)
                        }
                        if (node.y >= neighbour.y) {
                            yFirst += Math.abs(neighbour.y - node.y)
                            ySecond += - Math.abs(neighbour.y - node.y)
                        } else {
                            yFirst += -Math.abs(neighbour.y - node.y)
                            ySecond += Math.abs(neighbour.y - node.y)
                        }

                        if (xFirst >= 0 && xFirst <= maxXIndex && yFirst >= 0 && yFirst <= maxYIndex) {
                            //We are inside
                            nodes.forEach { old ->
                                if (old.x == xFirst && old.y == yFirst) {
                                    old.antinodeSigns.add(node.antennaSign)
                                }
                            }
                        }

                        if (xSecond >= 0 && xSecond <= maxXIndex && ySecond >= 0 && ySecond <= maxYIndex) {
                            //We are inside
                            nodes.forEach { old ->
                                if (old.x == xSecond && old.y == ySecond) {
                                    old.antinodeSigns.add(node.antennaSign)
                                }
                            }
                        }

                        if(xFirst <= 0 || xFirst >= maxXIndex) {
                            if(xSecond <= 0 || xSecond >= maxXIndex) {
                                if(yFirst <= 0 || yFirst >= maxYIndex) {
                                    if (ySecond <= 0 || ySecond >= maxYIndex) {
                                        lol = false
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        val antiNodes = nodes.filter { it.antinodeSigns.isNotEmpty() }.size

        print(antiNodes)

    }


    data class Node(
        val antennaSign: Char,
        val x: Int,
        val y: Int,
        var antinodeSigns: MutableList<Char>,
    )

    private fun Node.getMyNeighbours(possibleNeighbours: List<Node>): List<Node> {
        val myNeighbours = possibleNeighbours.filter { posNeighb ->
            if (posNeighb.x == this.x && posNeighb.y == this.y) {
                false
            } else {
                true
            }
        }

        return myNeighbours

    }
}


