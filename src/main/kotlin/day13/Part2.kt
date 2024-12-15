package day13

import utils.Utils

fun main() {
  Utils.setInputIoStartTime()
  val input = Utils.getInput(13)
  Utils.setInputParseStartTime()
  val output = parseInput(input)
  Utils.setAlgorithmStartTime()
  val aCost = 3
  val bCost = 1
  val offset = 10000000000000L
  var total = 0L
  for (triple in output) {
    val (a, b) = solve(Triple(triple.first, triple.second, Pair(triple.third.first + offset, triple.third.second + offset)))
    total += a * aCost + b * bCost
  }
  println(Utils.getElapsedTime())
  println("Total: $total")
}