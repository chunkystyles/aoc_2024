package day06

import utils.*

fun main() {
  setInputIoStartTime()
  val input = getInput(6)
  setInputParseStartTime()
  val output = parseInput(input)
  setAlgorithmStartTime()
  var guard = Guard(0, 0, 'X')
  for (i in output.indices) {
    for (j in output[i].indices) {
      if (output[i][j] != '.' && output[i][j] != '#') {
        guard = Guard(j, i, output[i][j])
        output[i][j] = 'X'
        break
      }
    }
  }
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
    guard.x = nextX
    guard.y = nextY
    output[guard.y][guard.x] = 'X'
  }
  println(output.joinToString("\n") { it.joinToString("") })
  val count = output.flatten().filter { it == 'X' }.size
  println("Count: $count")
  println(getElapsedTime())
}

fun isOutOfBounds(x: Int, y: Int, output: List<List<Char>>): Boolean {
  return x < 0 || x >= output[0].size || y < 0 || y >= output.size
}

fun getNextX(guard: Guard): Int {
  return when (guard.direction) {
    '>' -> guard.x + 1
    '<' -> guard.x - 1
    else -> guard.x
  }
}

fun getNextY(guard: Guard): Int {
  return when (guard.direction) {
    '^' -> guard.y - 1
    'V' -> guard.y + 1
    else -> guard.y
  }
}

fun getRotatedDirection(direction: Char): Char {
  return when (direction) {
    '^' -> '>'
    '>' -> 'V'
    'V' -> '<'
    '<' -> '^'
    else -> throw Exception("Invalid direction $direction")
  }
}

class Guard(var x: Int, var y: Int, var direction: Char) {
  fun getKey(): String {
    return "$y-$x-$direction"
  }
}

fun parseInput(input: String): MutableList<MutableList<Char>> {
  return input.split("\n").map { it.toMutableList() }.filter { it.isNotEmpty() }.toMutableList()
}