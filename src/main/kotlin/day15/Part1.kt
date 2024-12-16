package day15

import utils.*

fun main() {
  setInputIoStartTime()
  val input = getInput(15)
  setInputParseStartTime()
  val (map, moves) = parseInput(input)
  setAlgorithmStartTime()
  var robot = 0 to 0
  for (y in map.indices) {
    for (x in map[y].indices) {
      if (map[y][x] == Space.ROBOT) {
        robot = x to y
        break
      }
    }
  }
  for (move in moves) {
    robot = wholeMove(map, robot, move)
  }
  var total = 0
  for ((y, row) in map.withIndex()) {
    for ((x, space) in row.withIndex()) {
      if (space == Space.BOX) {
        total += y * 100 + x
      }
    }
  }
  println(getElapsedTime())
  println("Total: $total")
}

// Just because I haven't messed with enums in Kotlin
enum class Space {
  ROBOT, WALL, EMPTY, BOX, BOX_LEFT, BOX_RIGHT
}

enum class Direction {
  UP, DOWN, LEFT, RIGHT
}

fun parseInput(input: String): Pair<MutableList<MutableList<Space>>, List<Direction>> {
  val split = input.split("\n")
  val map = mutableListOf<MutableList<Space>>()
  var i = 0
  while (i < split.size && split[i].isNotBlank()) {
    map.add(
      split[i]
        .split("")
        .filter { it.isNotBlank() }
        .map { space ->
          when (space) {
            "#" -> Space.WALL
            "." -> Space.EMPTY
            "@" -> Space.ROBOT
            "O" -> Space.BOX
            else -> throw IllegalArgumentException("Invalid space: $space")
          }
        }.toMutableList())
    i++
  }
  i++
  val moves: MutableList<Direction> = mutableListOf()
  for (j in i until split.size) {
    moves.addAll(
      split[j].split("")
        .filter { it.isNotBlank() }
        .map {
          when (it) {
            "^" -> Direction.UP
            "v" -> Direction.DOWN
            "<" -> Direction.LEFT
            ">" -> Direction.RIGHT
            else -> throw IllegalArgumentException("Invalid direction: $it")
          }
        }
    )
  }
  return map to moves
}

fun wholeMove(map: MutableList<MutableList<Space>>, robot: Pair<Int, Int>, direction: Direction): Pair<Int, Int> {
  val intendedSpace = singleMove(robot, direction)
  if (!isInBounds(map, intendedSpace) || map[intendedSpace.second][intendedSpace.first] == Space.WALL) {
    return robot
  }
  if (map[intendedSpace.second][intendedSpace.first] == Space.EMPTY) {
    map[intendedSpace.second][intendedSpace.first] = Space.ROBOT
    map[robot.second][robot.first] = Space.EMPTY
    return intendedSpace
  }
  var nextSpace = singleMove(intendedSpace, direction)
  while (map[nextSpace.second][nextSpace.first] == Space.BOX) {
    nextSpace = singleMove(nextSpace, direction)
  }
  if (map[nextSpace.second][nextSpace.first] == Space.WALL) {
    return robot
  }
  map[nextSpace.second][nextSpace.first] = Space.BOX
  map[intendedSpace.second][intendedSpace.first] = Space.ROBOT
  map[robot.second][robot.first] = Space.EMPTY
  return intendedSpace
}

fun singleMove(space: Pair<Int, Int>, direction: Direction): Pair<Int, Int> {
  return when (direction) {
    Direction.UP -> space.first to space.second - 1
    Direction.DOWN -> space.first to space.second + 1
    Direction.LEFT -> space.first - 1 to space.second
    Direction.RIGHT -> space.first + 1 to space.second
  }
}

fun isInBounds(map: List<List<Space>>, space: Pair<Int, Int>): Boolean {
  return space.second in map.indices && space.first in map[space.second].indices
}

fun printMap(map: List<List<Space>>) {
  for (row in map) {
    for (space in row) {
      print(
        when (space) {
          Space.WALL -> "#"
          Space.EMPTY -> "."
          Space.ROBOT -> "@"
          Space.BOX -> "O"
          Space.BOX_LEFT -> "["
          Space.BOX_RIGHT -> "]"
        }
      )
    }
    println()
  }
}