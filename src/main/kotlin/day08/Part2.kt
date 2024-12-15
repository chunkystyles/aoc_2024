package day08

import utils.*

fun main() {
  setInputIoStartTime()
  val input = getInput(8)
  setInputParseStartTime()
  val output = parseInput(input)
  val antinodes = mutableSetOf<String>()
  setAlgorithmStartTime()
  output.values.forEach() { list ->
    for (i in list.indices) {
      val left = list[i]
      antinodes.add("${left.first},${left.second}")
      for (j in list.indices) {
        if (i == j) {
          continue
        }
        val right = list[j]
        val distX = right.first - left.first
        val distY = right.second - left.second
        var newX = right.first
        var newY = right.second
        while (true) {
          newX += distX
          newY += distY
          if (newX < 0 || newX >= width || newY < 0 || newY >= height) {
            break
          }
          antinodes.add("$newX,$newY")
        }
      }
    }
  }
  val total = antinodes.size
  println(getElapsedTime())
  println("Antinodes: $total")
}