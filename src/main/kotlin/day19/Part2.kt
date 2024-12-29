package day19

import utils.*

val map = mutableMapOf<String, Long>()
var towels: List<String> = emptyList()

fun main() {
  setInputIoStartTime()
  val input = getInput(19)
  setInputParseStartTime()
  val output = parseInput(input)
  towels = output.first
  val designs = output.second
  setAlgorithmStartTime()
  var count = 0L
  designs.forEach() { design -> count += countValidDesigns(design) }
  println(getElapsedTime())
  println(count)
}

fun countValidDesigns(design: String): Long {
  if (map.contains(design)) {
    return map[design]!!
  }
  var count = 0L
  for (towel in towels) {
    if (design == towel) {
      count++
    } else if (design.startsWith(towel)) {
      count += countValidDesigns(design.substring(towel.length))
    }
  }
  map[design] = count
  return count
}