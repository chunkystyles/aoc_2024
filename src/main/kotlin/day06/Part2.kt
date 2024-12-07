package day06

import utils.Utils

fun main() {
  Utils.setInputIoStartTime()
  val input = Utils.getInput(6)
  Utils.setInputParseStartTime()
  val output = parseInput(input)
  Utils.setAlgorithmStartTime()
  val guardSet = mutableSetOf<String>()
  var guard = Guard(0, 0, 'X')
  for (i in output.indices) {
    for (j in output[i].indices) {
      if (output[i][j] != '.' && output[i][j] != '#') {
        guard = Guard(j, i, output[i][j])
        guardSet.add(guard.getKey())
        break
      }
    }
  }
  var obstacleCount = 0
  while (true) {
    var nextX = getNextX(guard)
    var nextY = getNextY(guard)
    if (isOutOfBounds(nextX, nextY, output)) {
      break
    }
    while (output[nextY][nextX] == '#') {
      guard.direction = getRotatedDirection(guard.direction)
      nextX = getNextX(guard)
      nextY = getNextY(guard)
    }
    if (output[nextY][nextX] == '.') {
      val previousX = guard.x
      val previousY = guard.y
      val newGuard = Guard(previousX, previousY, getRotatedDirection(guard.direction))
      val originalChar = output[nextY][nextX]
      output[nextY][nextX] = '#'
      if (secondLoop(newGuard, output, guardSet)) {
        output[nextY][nextX] = 'O'
        obstacleCount++
      } else {
        output[nextY][nextX] = originalChar
      }
    }
    guard.x = nextX
    guard.y = nextY
    output[guard.y][guard.x] = guard.direction
    guardSet.add(guard.getKey())
  }
  println("Count: $obstacleCount")
  println(Utils.getElapsedTime())
}

fun secondLoop(guard: Guard, output: List<List<Char>>, guardSet: MutableSet<String>): Boolean {
  val newGuardSet = guardSet.toMutableSet()
  while (true) {
    var nextX = getNextX(guard)
    var nextY = getNextY(guard)
    if (isOutOfBounds(nextX, nextY, output)) {
      return false
    }
    while (output[nextY][nextX] == '#') {
      guard.direction = getRotatedDirection(guard.direction)
      nextX = getNextX(guard)
      nextY = getNextY(guard)
    }
    guard.x = nextX
    guard.y = nextY
    if (newGuardSet.contains(guard.getKey())) {
      return true
    }
    newGuardSet.add(guard.getKey())
  }
}