package day18

import java.util.PriorityQueue

import utils.*
import kotlin.math.abs

val cardinalDirections = listOf(
  -1 to 0, 1 to 0, 0 to -1, 0 to 1 // cardinal directions
)
val diagonalDirections = listOf(
  -1 to -1, -1 to 1, 1 to -1, 1 to 1 // diagonal directions
)
val bothDirections = cardinalDirections + diagonalDirections

val width = 71
val height = 71

fun main() {
  setInputIoStartTime()
  val input = getInput(18)
  setInputParseStartTime()
  val obstacles = parseInput(input)
  setAlgorithmStartTime()
  val map = MutableList(height) { MutableList(width) { 0 } }
  val start = 0 to 0
  val goal = width -1 to height - 1
  for (i in 0..1023) {
    if (i >= obstacles.size) {
      break
    }
    map[obstacles[i].second][obstacles[i].first] = Int.MAX_VALUE
  }
  val moves = aStar(map, start, goal)
  println(getElapsedTime())
  println("Moves: ${moves.size - 1}")
}

fun parseInput(input: String): List<Pair<Int, Int>> {
  return input
    .split("\n")
    .filter { it.isNotEmpty() }
    .map {
      val split = it.split(",")
      split[0].toInt() to split[1].toInt()
    }
}

data class Node(val x: Int, val y: Int, val g: Int, val h: Int, val parent: Node?) : Comparable<Node> {
  val f: Int
    get() = g + h

  override fun compareTo(other: Node): Int {
    return this.f - other.f
  }
}

fun aStar(map: List<List<Int>>, start: Pair<Int, Int>, goal: Pair<Int, Int>): List<Pair<Int, Int>> {
  val openSet = PriorityQueue<Node>()
  val closedSet = mutableSetOf<Pair<Int, Int>>()
  val startNode = Node(start.second, start.first, 0, heuristic(start, goal), null)
  openSet.add(startNode)
  while (openSet.isNotEmpty()) {
    val current = openSet.poll()
    if (current.x == goal.second && current.y == goal.first) {
      return reconstructPath(current)
    }
    closedSet.add(current.y to current.x)
    for (direction in cardinalDirections) {
      val neighborX = current.x + direction.second
      val neighborY = current.y + direction.first
      if (neighborX !in map.indices || neighborY !in map[0].indices || map[neighborY][neighborX] > 0) {
        continue
      }
      val neighbor = Node(neighborX, neighborY, current.g + 1, heuristic(neighborX to neighborY, goal), current)
      if (neighbor.y to neighbor.x in closedSet) {
        continue
      }
      if (openSet.none { it.x == neighbor.x && it.y == neighbor.y && it.f <= neighbor.f }) {
        openSet.add(neighbor)
      }
    }
  }
  return emptyList() // No path found
}

fun heuristic(a: Pair<Int, Int>, b: Pair<Int, Int>): Int {
  return abs(a.first - b.first) + abs(a.second - b.second)
}

fun reconstructPath(node: Node): List<Pair<Int, Int>> {
  var current: Node? = node
  val path = mutableListOf<Pair<Int, Int>>()
  while (current != null) {
    path.add(current.x to current.y)
    current = current.parent
  }
  return path.reversed()
}