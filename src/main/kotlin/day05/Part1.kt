package day05

import utils.Utils

fun main() {
  Utils.setInputIoStartTime()
  val input = Utils.getInput(5)
  Utils.setInputParseStartTime()
  val output = parseInput(input)
  val pageOrders = output.first
  val updates = output.second
  Utils.setAlgorithmStartTime()
  val total = updates.sumOf { getMiddleNumberOfValidUpdate(pageOrders, it) }
  println("Total: $total")
  println(Utils.getElapsedTime())
}

fun parseInput(input: String): Pair<Set<String>, List<List<Int>>> {
  val pageOrders: MutableSet<String> = mutableSetOf()
  val updates: MutableList<MutableList<Int>> = mutableListOf()
  input.split("\n").forEach { line ->
    if (line.contains("|")) {
      pageOrders.add(line)
    } else if (line.contains(",")) {
      updates.add(line.split(",").map { it.toInt() }.toMutableList())
    }
  }
  return pageOrders to updates
}

fun getMiddleNumberOfValidUpdate(pageOrders: Set<String>, update: List<Int>): Int {
  for (i in update.indices) {
    val left = update[i]
    for (j in i + 1 until update.size) {
      val right = update[j]
      if (!pageOrders.contains("$left|$right")) {
        return 0
      }
    }
  }
  return update[update.size / 2]
}