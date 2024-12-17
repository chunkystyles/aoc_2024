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
  val bestPathNodes = mutableSetOf<Pair<Int, Int>>()
  var found = false
  while (queue.isNotEmpty()) {
    val newQueue = queue.sortedBy { it.cost }.toMutableList() // This should really be a binary tree, but I'm lazy
    val node = newQueue.removeFirst()
    val pastDirection = node.direction
    queue = newQueue
    val (x, y) = node.point
    for (newDirection in directions) {
      if (isOppositeDirection(pastDirection, newDirection.key)) {
        continue
      }
      if (found && node.cost > finalCost) {
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
        if (found && cost > finalCost) {
          continue
        }
        val nextNode = Node(cost, newDirection.key, newX to newY, node)
        if (map[newY][newX] == 'E') {
          if (found) {
            addNodesToBestPathSet(nextNode, bestPathNodes)
          } else {
            finalCost = cost
            addNodesToBestPathSet(nextNode, bestPathNodes)
            found = true
          }
        } else if (newX to newY to pastDirection !in visited) {
          queue.add(nextNode)
        }
      }
    }
  }
  println("Elapsed time: ${getElapsedTime()}")
  println("Best path nodes: ${bestPathNodes.size}")
}

fun addNodesToBestPathSet(node: Node, bestPathNodes: MutableSet<Pair<Int, Int>>) {
  var currentNode: Node? = node
  while (currentNode != null) {
    bestPathNodes.add(currentNode.point)
    currentNode = currentNode.parent
  }
}