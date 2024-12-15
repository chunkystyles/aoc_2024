package day14

import utils.*

fun main() {
  setInputIoStartTime()
  val input = getInput(14)
  setInputParseStartTime()
  val robots = parseInput(input)
  setAlgorithmStartTime()
  val horizontalBoundary = 101
  val verticalBoundary = 103
  var i = 1
  while (true) {
    for (robot in robots) {
      robot.move(verticalBoundary, horizontalBoundary)
    }
    if (areNoRobotsInTheSameSpot(robots, verticalBoundary, horizontalBoundary)) {
      break
    }
    i++
  }
  println(getElapsedTime())
  printMap(robots, verticalBoundary, horizontalBoundary)
  println("i = $i")
}

fun areNoRobotsInTheSameSpot(robots: List<Robot>, verticalBoundary: Int, horizontalBoundary: Int): Boolean {
  val set = mutableSetOf<Pair<Int, Int>>()
  for (robot in robots) {
    if (set.contains(Pair(robot.x, robot.y))) {
      return false
    } else {
      set.add(Pair(robot.x, robot.y))
    }
  }
  return true
}