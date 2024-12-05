package day05

import utils.Utils

fun main() {
  Utils.setInputIoStartTime()
  val input = Utils.getInput(5)
  Utils.setInputParseStartTime()
  val output = parseInput2(input)
  val pageOrderPairs = output.pageOrderPairs
  val pageOrderSet = output.pageOrderSet
  val updates = output.updates
  Utils.setAlgorithmStartTime()
  var total = 0
  updates.forEach {
    if (getMiddleNumberOfValidUpdate(pageOrderSet, it) == 0) {
      total += getMiddleNumberOfInvalidUpdate(pageOrderPairs, it)
    }
  }
  println("Total: $total")
  println(Utils.getElapsedTime())
}

class Output (val pageOrderPairs: Map<Int, Set<Int>>, val pageOrderSet: Set<String>, val updates: List<List<Int>>)

fun parseInput2(input: String): Output {
  val pageOrderPairs: MutableMap<Int, MutableSet<Int>> = mutableMapOf()
  val pageOrderSet: MutableSet<String> = mutableSetOf()
  val updates: MutableList<MutableList<Int>> = mutableListOf()
  input.split("\n").forEach { line ->
    if (line.contains("|")) {
      pageOrderSet.add(line)
      val (left, right) = line.split("|").map { it.toInt() }
      if (pageOrderPairs.containsKey(left)) {
        pageOrderPairs[left]!!.add(right)
      } else {
        pageOrderPairs[left] = mutableSetOf(right)
      }
    } else if (line.contains(",")) {
      updates.add(line.split(",").map { it.toInt() }.toMutableList())
    }
  }
  return Output(pageOrderPairs, pageOrderSet, updates)
}

fun getMiddleNumberOfInvalidUpdate(pageOrders: Map<Int, Set<Int>>, update: List<Int>): Int {
  val counts = mutableListOf<Pair<Int, Int>>()
  for (i in update.indices) {
    val left = update[i]
    var count = 0
    val leftSets = pageOrders[left]
    if (leftSets == null) {
      counts.add(left to 0)
      continue
    }
    for (j in update.indices) {
      if (i == j) {
        continue
      }
      val right = update[j]
      if (leftSets.contains(right)) {
        count++
      }
    }
    counts.add(left to count)
  }
  counts.sortByDescending { it.second }
  return counts[counts.size / 2].first
}