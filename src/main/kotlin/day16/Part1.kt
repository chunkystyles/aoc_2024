package day16

import utils.*

fun main() {
  setInputIoStartTime()
  val input = getInput(16)
  setInputParseStartTime()
  val (startPoint, map) = parseInput(input)
  setAlgorithmStartTime()
  val directions = mapOf(
    Direction.UP to (0 to -1),
    Direction.DOWN to (0 to 1),
    Direction.LEFT to (-1 to 0),
    Direction.RIGHT to (1 to 0)
  )
  var finalCost = Int.MAX_VALUE
  val visited = mutableSetOf<Pair<Pair<Int, Int>, Direction>>()
  val startNode = Node(0, Direction.RIGHT, startPoint)
  var queue = mutableListOf(startNode)
  while (queue.isNotEmpty()) {
    val newQueue = queue.sortedBy { it.cost }.toMutableList() // This should really be a binary tree, but I'm lazy
    val node = newQueue.removeFirst()
    val pastDirection = node.direction
    queue = newQueue
    val (x, y) = node.point
    var found = false
    for (newDirection in directions) {
      if (isOppositeDirection(pastDirection, newDirection.key)) {
        continue
      }
      visited.add(x to y to pastDirection)
      val (dx, dy) = newDirection.value
      val (newX, newY) = x + dx to y + dy
      if (visited.contains(newX to newY to pastDirection)) {
        continue
      }
      if (isInBounds(newX to newY, map) && (map[newY][newX] == '.' || map[newY][newX] == 'E')) {
        val cost = if (pastDirection == newDirection.key) node.cost + 1 else node.cost + 1001
        val nextNode = Node(cost, newDirection.key, newX to newY)
        if (map[newY][newX] == 'E') {
          finalCost = cost
          found = true
          break
        } else if (newX to newY to pastDirection !in visited) {
          queue.add(nextNode)
        }
      }
    }
    if (found) {
      break
    }
  }
  println("Elapsed time: ${getElapsedTime()}")
  println("Final cost: $finalCost")
}

class Node(
  val cost: Int,
  val direction: Direction,
  val point: Pair<Int, Int>,
  var parent: Node? = null // used in part 2
)

enum class Direction {
  UP, DOWN, LEFT, RIGHT
}

fun parseInput(input: String): Pair<Pair<Int, Int>, List<List<Char>>> {
  var start = -1 to -1
  val map = input.split("\n")
    .filter { it.isNotBlank() }
    .mapIndexed { y, line ->
      line.mapIndexed { x, char ->
        if (char == 'S') {
          start = x to y
        }
        char
      }
    }
  return start to map
}

fun isInBounds(point: Pair<Int, Int>, map: List<List<Char>>): Boolean {
  val (x, y) = point
  return x >= 0 && y >= 0 && y < map.size && x < map[y].size
}

fun isOppositeDirection(direction1: Direction, direction2: Direction): Boolean = when (direction1) {
  Direction.UP -> direction2 == Direction.DOWN
  Direction.DOWN -> direction2 == Direction.UP
  Direction.LEFT -> direction2 == Direction.RIGHT
  Direction.RIGHT -> direction2 == Direction.LEFT
}