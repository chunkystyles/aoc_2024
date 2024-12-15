package day10

import utils.*

fun main() {
  setInputIoStartTime()
  val input = getInput(10)
  setInputParseStartTime()
  val output = parseInput(input)
  setAlgorithmStartTime()
  buildConnections(output)
  var count = 0
  for (row in output) {
    for (point in row) {
      if (point.value == 0 && point.connections.isNotEmpty()) {
        val trails = mutableSetOf<String>()
        val connections = point.connections.toMutableList()
        while (connections.isNotEmpty()) {
          val current = connections.removeAt(0)
          if (current.value == 9) {
            trails.add(current.id)
          } else if (current.connections.isNotEmpty()) {
            connections.addAll(current.connections)
          }
        }
        count += trails.size
      }
    }
  }
  println(getElapsedTime())
  println("count = $count")
}

fun buildConnections(output: List<List<Point>>) {
  for (i in output.indices) {
    for (j in output[i].indices) {
      if (i > 0 && output[i][j].value + 1 == output[i - 1][j].value) {
        output[i][j].connections.add(output[i - 1][j])
      }
      if (i < output.size - 1 && output[i][j].value + 1 == output[i + 1][j].value) {
        output[i][j].connections.add(output[i + 1][j])
      }
      if (j > 0 && output[i][j].value + 1 == output[i][j - 1].value) {
        output[i][j].connections.add(output[i][j - 1])
      }
      if (j < output[i].size - 1 && output[i][j].value + 1 == output[i][j + 1].value) {
        output[i][j].connections.add(output[i][j + 1])
      }
    }
  }
}

class Point(
  val value: Int,
  val id: String,
  val connections: MutableList<Point> = mutableListOf()
)

fun parseInput(input: String): List<List<Point>> {
  return input
    .split("\n")
    .filter { it.isNotEmpty() }
    .mapIndexed() { i, line ->
      line.split("")
        .filter { it.isNotBlank() }
        .mapIndexed { j, value ->
          Point(value.toInt(), "$i-$j")
        }
    }
}