package day18

import utils.*

var lastPlaced = 0 to 0
val obstacleMap = MutableList(height) { MutableList(width) { NodeType.EMPTY } }

fun main() {
  setInputIoStartTime()
  val input = getInput(18)
  setInputParseStartTime()
  val obstacles = parseInput(input)
  setAlgorithmStartTime()
  addObstaclesUntilPathIsBlocked(obstacles)
  println(getElapsedTime())
}

enum class NodeType {
  NOT_TOUCHING,
  BOTTOM_LEFT,
  TOP_RIGHT,
  MEETING,
  EMPTY
}

fun addObstaclesUntilPathIsBlocked(obstacles: List<Pair<Int, Int>>) {
  for (obstacle in obstacles) {
    lastPlaced = obstacle
    val myNodeType =
      updateNodeType(obstacle.first, obstacle.second, getPositionNodeType(obstacle.first, obstacle.second))
    if (myNodeType == NodeType.MEETING) {
      println("Path is blocked at ${obstacle.first},${obstacle.second}")
      break
    }
    obstacleMap[obstacle.second][obstacle.first] = myNodeType
  }
}

fun getPositionNodeType(x: Int, y: Int): NodeType {
  return when {
    x == 0 && y == 0 -> NodeType.MEETING
    x == 0 -> NodeType.BOTTOM_LEFT
    y == 0 -> NodeType.TOP_RIGHT
    x == width - 1 && y == height - 1 -> NodeType.MEETING
    x == width - 1 -> NodeType.TOP_RIGHT
    y == height - 1 -> NodeType.BOTTOM_LEFT
    else -> NodeType.NOT_TOUCHING
  }
}

fun updateNodeType(
  x: Int,
  y: Int,
  nodeType: NodeType
): NodeType {
  var myNodeType = nodeType
  val nodes = mutableListOf<Pair<Int, Int>>()
  for (direction in bothDirections) {
    val neighborX = x + direction.second
    val neighborY = y + direction.first
    if (neighborX !in obstacleMap.indices || neighborY !in obstacleMap[0].indices) {
      continue
    }
    val neighborNodeType = obstacleMap[neighborY][neighborX]
    if (neighborNodeType != NodeType.EMPTY) {
      nodes.add(neighborX to neighborY)
    }
  }
  for (pair in nodes.filter { obstacleMap[it.second][it.first] != NodeType.NOT_TOUCHING }) {
    val neighborNodeType = obstacleMap[pair.second][pair.first]
    if (neighborNodeType != myNodeType) {
      if (myNodeType == NodeType.NOT_TOUCHING) {
        myNodeType = neighborNodeType
      } else {
        return NodeType.MEETING
      }
    }
  }
  if (myNodeType != NodeType.NOT_TOUCHING) {
    for (pair in nodes.filter { obstacleMap[it.second][it.first] == NodeType.NOT_TOUCHING }) {
      obstacleMap[pair.second][pair.first] = myNodeType
      updateNodeType(pair.first, pair.second, myNodeType)
    }
  }
  return myNodeType
}