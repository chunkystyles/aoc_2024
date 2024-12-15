package day07

import utils.*

fun main() {
  setInputIoStartTime()
  val input = getInput(7)
  setInputParseStartTime()
  val output = parseInput(input)
  setAlgorithmStartTime()
  var total = 0L
  output.forEach() { line ->
    val testValue = line.first
    val equation = line.second
    var results = mutableListOf(equation[0].toLong())
    for (i in 1..<equation.size) {
      val newResults = mutableListOf<Long>()
      results.forEach() {
        newResults.add(it + equation[i])
        newResults.add(it * equation[i])
      }
      results = newResults
    }
    if (results.contains(testValue)) {
      total += testValue
    }
  }
  println(getElapsedTime())
  println("Total: $total")
}

fun parseInput(input: String): List<Pair<Long, List<Int>>> {
  return input.split("\n").filter { it.isNotBlank() }.map {
    val split = it.split(": ")
    Pair(split[0].toLong(), split[1].split(" ").map { it.toInt() })
  }
}