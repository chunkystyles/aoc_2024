package day19

import utils.*

fun main() {
  setInputIoStartTime()
  val input = getInput(19)
  setInputParseStartTime()
  val (towels, designs) = parseInput(input)
  setAlgorithmStartTime()
  var count = 0
  designs.forEach() {
    if (isDesignValid(towels, it)) {
      count++
    }
  }
  println(getElapsedTime())
  println(count)
}

fun isDesignValid(towels: List<String>, design: String): Boolean {
  val stack = mutableListOf(design)
  while (stack.isNotEmpty()) {
    val subDesign = stack.removeAt(0)
    for (towel in towels) {
      if (subDesign == towel) {
        return true
      } else if (subDesign.startsWith(towel)) {
        stack.addFirst(subDesign.substring(towel.length))
      }
    }
  }
  return false
}

fun parseInput(input: String): Pair<List<String>, List<String>> {
  val split = input.split("\n").filter { it.isNotEmpty() }
  return split[0].split(", ").toList() to split.slice(1 until split.size).toList()
}