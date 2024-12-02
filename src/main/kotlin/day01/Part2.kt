package day01

import utils.Utils

fun main() {
  Utils.setStartTime()
  val input = Utils.getInput(1)
  val listPair = parseInput(input)
  val list1 = listPair.first
  val list2 = listPair.second
  list1.sort()
  val map = mutableMapOf<Int, Int>()
  list2.map() { map[it] = map.getOrDefault(it, 0) + 1 }
  var total = 0
  list1.forEach() {
    if (map.containsKey(it)) {
      total += it * map[it]!!
    }
  }
  println("Total: $total")
  println("Elapsed time: ${Utils.getElapsedTime()}")
}