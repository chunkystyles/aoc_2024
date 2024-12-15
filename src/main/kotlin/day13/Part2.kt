package day13

import utils.*

fun main() {
  setInputIoStartTime()
  val input = getInput(13)
  setInputParseStartTime()
  val output = parseInput(input)
  setAlgorithmStartTime()
  val aCost = 3
  val bCost = 1
  val offset = 10000000000000L
  var total = 0L
  for (triple in output) {
    val (a, b) = solve(Triple(triple.first, triple.second, Pair(triple.third.first + offset, triple.third.second + offset)))
    total += a * aCost + b * bCost
  }
  println(getElapsedTime())
  println("Total: $total")
}