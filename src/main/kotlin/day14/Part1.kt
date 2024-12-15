package day14

import utils.Utils

fun main() {
  Utils.setInputIoStartTime()
  val input = Utils.getInput(14)
  Utils.setInputParseStartTime()
  val robots = parseInput(input)
  Utils.setAlgorithmStartTime()
  val horizontalBoundary = 101
  val verticalBoundary = 103
  for (i in 0 until 99) {
    for (robot in robots) {
      robot.move(verticalBoundary, horizontalBoundary)
    }
  }
  val middleHorizontal = horizontalBoundary / 2
  val middleVertical = verticalBoundary / 2
  val firstHalfHorizontal = middleHorizontal - 1
  val firstHalfVertical = middleVertical - 1
  val otherHalfHorizontalBoundary = middleHorizontal + 1
  val otherHalfVerticalBoundary = middleVertical + 1
  val quadrants = listOf(
    Pair(Pair(0, 0), Pair(firstHalfHorizontal, firstHalfVertical)),
    Pair(Pair(otherHalfHorizontalBoundary, 0), Pair(horizontalBoundary, firstHalfVertical)),
    Pair(Pair(0, otherHalfVerticalBoundary), Pair(firstHalfHorizontal, verticalBoundary)),
    Pair(Pair(otherHalfHorizontalBoundary, otherHalfVerticalBoundary), Pair(horizontalBoundary, verticalBoundary))
  )
  val counts = mutableListOf(0, 0, 0, 0)
  for (robot in robots) {
    for ((index, quadrant) in quadrants.withIndex()) {
      if (isRobotInArea(robot, quadrant.first.first, quadrant.first.second, quadrant.second.first, quadrant.second.second)) {
        counts[index]++
        break
      }
    }
  }
  println(Utils.getElapsedTime())
  var total = 1
  for (count in counts) {
    if (count > 0) {
      total *= count
    }
  }
  println("Total: $total")
}

class Robot(
  var x: Int,
  var y: Int,
  var vx: Int,
  var vy: Int
) {
  override fun toString(): String {
    return "Robot(x=$x, y=$y, vx=$vx, vy=$vy)"
  }
  fun move(verticalBoundary: Int, horizontalBoundary: Int) {
    x += vx
    y += vy
    if (x < 0) {
      x += horizontalBoundary
    }
    if (x >= horizontalBoundary) {
      x -= horizontalBoundary
    }
    if (y < 0) {
      y += verticalBoundary
    }
    if (y >= verticalBoundary) {
      y -= verticalBoundary
    }
  }
}

fun isRobotInArea(robot: Robot, x1: Int, y1: Int, x2: Int, y2: Int): Boolean {
  return robot.x in x1..x2 && robot.y in y1..y2
}

fun parseInput(input: String): List<Robot> {
  val pattern = Regex("""p=(\d{1,3}),(\d{1,3}) v=(-?\d{1,3}),(-?\d{1,3})""").toPattern()
  return input.split("\n")
    .filter { it.isNotEmpty() }
    .map {
      val matcher = pattern.matcher(it)
      if (matcher.find()) {
        Robot(
          matcher.group(1).toInt(),
          matcher.group(2).toInt(),
          matcher.group(3).toInt(),
          matcher.group(4).toInt()
        )
      } else {
        throw Exception("Invalid input")
      }
    }
}

fun printMap(robots: List<Robot>, verticalBoundary: Int, horizontalBoundary: Int) {
  val map = Array(verticalBoundary) { Array(horizontalBoundary) { ' ' } }
  for (robot in robots) {
    if (map[robot.y][robot.x] == ' ') {
      map[robot.y][robot.x] = '1'
    } else {
      map[robot.y][robot.x] = map[robot.y][robot.x] + 1
    }
  }
  for (row in map) {
    println(row.joinToString(""))
  }
}