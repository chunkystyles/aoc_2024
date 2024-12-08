package day08

import utils.Utils

var height = 0
var width = 0

fun main() {
  Utils.setInputIoStartTime()
  val input = Utils.getInput(8)
  Utils.setInputParseStartTime()
  val output = parseInput(input)
  val antinodes = mutableSetOf<String>()
  Utils.setAlgorithmStartTime()
  output.values.forEach() { list ->
    for (i in list.indices) {
      val left = list[i]
      for (j in list.indices) {
        if (i == j) {
          continue
        }
        val right = list[j]
        val distX = right.first - left.first
        val distY = right.second - left.second
        val newX = right.first + distX
        val newY = right.second + distY
        if (newX < 0 || newX >= width || newY < 0 || newY >= height) {
          continue
        }
        antinodes.add("$newX,$newY")
      }
    }
  }
  val total = antinodes.size
  println(Utils.getElapsedTime())
  println("Antinodes: $total")
}

fun parseInput(input: String): Map<Char, List<Pair<Int, Int>>> {
  val map = mutableMapOf<Char, List<Pair<Int, Int>>>()
  input.split("\n").filter { line -> line.isNotBlank() }.forEach() { line ->
    line.split("").filter{ it.isNotBlank() }.map{ it[0] }.forEachIndexed() { index, char ->
      if (char != '.') {
        map.putIfAbsent(char, mutableListOf())
        map[char] = map[char]!!.plus(Pair(index, height))
      }
    }
    height++
    if (line.length > width) {
      width = line.length
    }
  }
  return map
}