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
        val connections = point.connections.toMutableList()
        while (connections.isNotEmpty()) {
          val current = connections.removeAt(0)
          if (current.value == 9) {
            count++
          } else if (current.connections.isNotEmpty()) {
            connections.addAll(current.connections)
          }
        }
      }
    }
  }
  println(getElapsedTime())
  println("count = $count")
}