import java.io.File

class Day14 {

    init {

        val robots = mutableListOf<Robot>()
        File("Day14.txt").useLines { lines ->
            lines.forEach { line ->
                val values = line.split(" ")
                var position = values[0].split("=")[1].split(",")
                var xPos = position[0].toInt()
                var yPos = position[1].toInt()
                var velocity = values[1].split("=")[1].split(",")
                var xVel = velocity[0].toInt()
                var yVel = velocity[1].toInt()
                robots.add(Robot(xPos, yPos, xVel, yVel))
            }
        }
        val maxX = 101
        val maxY = 103
        var quad1 = 0
        var quad2 = 0
        var quad3 = 0
        var quad4 = 0
        //repeat(100) {
            robots.forEach { robot ->
                /*var newX = robot.xPos + robot.xVel
                if (newX < 0) {
                    newX = 11 - newX
                } else if (newX > 10) {
                    newX = 11 - newX
                }*/
                robot.xPos = (robot.xPos + (100 * robot.xVel)) % maxX
                robot.yPos = (robot.yPos + (100 * robot.yVel)) % maxY

                if(robot.xPos < 0) {
                    robot.xPos = maxX - -robot.xPos
                } else if(robot.xPos > maxX-1) {
                    robot.xPos = maxX - robot.xPos
                }
                if(robot.yPos < 0) {
                    robot.yPos = maxY - -robot.yPos
                } else if(robot.yPos > maxY-1) {
                    robot.yPos = maxY - robot.yPos
                }
                //is left
                if(robot.xPos < (maxX-1)/2) {
                    //is Top
                    if(robot.yPos < (maxY-1)/2) {
                        quad1++
                    } else if(robot.yPos >= (maxY+1)/2) {
                        quad3++
                    }
                } else if (robot.xPos >= (maxX+1)/2) { //is right
                    if(robot.yPos < (maxY-1)/2) {
                        quad2++
                    } else if (robot.yPos >= (maxY+1)/2) {
                        quad4++
                    }
                }
            }
        //}

        println(quad1*quad2*quad3*quad4)

    }

    data class Robot(
        var xPos: Int,
        var yPos: Int,
        var xVel: Int,
        var yVel: Int
    )
}