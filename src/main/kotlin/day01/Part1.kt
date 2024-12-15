package day01

import utils.*
import kotlin.math.abs

fun main() {
  setStartTime()
  val input = getInput(1)
  val listPair = parseInput(input)
  val list1 = listPair.first
  val list2 = listPair.second
  list1.sort()
  list2.sort()
  var total = 0
  for (i in 0..<list1.size) {
    total += abs(list1[i] - list2[i])
  }
  println("Total: $total")
  println("Elapsed time: ${getElapsedTime()}")
}

fun parseInput(input: String): Pair<MutableList<Int>, MutableList<Int>> {
  val lines = input.split("\n").filter { it.isNotEmpty() }
  val list1 = mutableListOf<Int>()
  val list2 = mutableListOf<Int>()
  lines
    .map() { it.split("   ") }
    .forEach { list1.add(it[0].toInt()); list2.add(it[1].toInt()) }
  return list1 to list2
}