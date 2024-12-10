package day10

import utils.Utils

fun main() {
  Utils.setInputIoStartTime()
  val input = Utils.getInput(10)
  Utils.setInputParseStartTime()
  val output = parseInput(input)
  Utils.setAlgorithmStartTime()
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
  println(Utils.getElapsedTime())
  println("count = $count")
}