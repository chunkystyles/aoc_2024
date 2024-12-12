package day01

import utils.Utils
import kotlin.math.abs
import java.util.PriorityQueue

fun main() {
  Utils.setStartTime()
  val input = Utils.getInput(1)
  val listPair = parseInput(input)
  val list1 = listPair.first
  val list2 = listPair.second
  list1.sort()
  list2.sort()
  var total = 0
  for (i in 0..<list1.size) {
    total += abs(list1[i] - list2[i])
  }
  println("Total: $total")
  println("Elapsed time: ${Utils.getElapsedTime()}")

  // Test case for shortestPath function
  val grid = listOf(
    listOf(1, 3, 1),
    listOf(1, 5, 1),
    listOf(4, 2, 1)
  )
  val start = Pair(0, 0)
  val end = Pair(2, 2)
  val shortestPathLength = shortestPath(grid, start, end)
  println("Shortest path length: $shortestPathLength")
}

fun parseInput(input: String): Pair<MutableList<Int>, MutableList<Int>> {
  val lines = input.split("\n").filter { it.isNotEmpty() }
  val list1 = mutableListOf<Int>()
  val list2 = mutableListOf<Int>()
  lines
    .map() { it.split("   ") }
    .forEach { list1.add(it[0].toInt()); list2.add(it[1].toInt()) }
  return list1 to list2
}

fun shortestPath(grid: List<List<Int>>, start: Pair<Int, Int>, end: Pair<Int, Int>): Int {
  val directions = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0))
  val rows = grid.size
  val cols = grid[0].size
  val dist = Array(rows) { IntArray(cols) { Int.MAX_VALUE } }
  val pq = PriorityQueue<Triple<Int, Int, Int>>(compareBy { it.first })

  dist[start.first][start.second] = 0
  pq.add(Triple(0, start.first, start.second))

  while (pq.isNotEmpty()) {
    val (currentDist, x, y) = pq.poll()

    if (x == end.first && y == end.second) {
      return currentDist
    }

    for ((dx, dy) in directions) {
      val newX = x + dx
      val newY = y + dy

      if (newX in 0 until rows && newY in 0 until cols) {
        val newDist = currentDist + grid[newX][newY]

        if (newDist < dist[newX][newY]) {
          dist[newX][newY] = newDist
          pq.add(Triple(newDist, newX, newY))
        }
      }
    }
  }

  return -1
}
