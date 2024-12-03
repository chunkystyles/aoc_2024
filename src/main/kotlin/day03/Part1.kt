package day03

import utils.Utils

fun main() {
  Utils.setStartTime()
  val input = Utils.getInput(3)
  val output = parseInput(input);
  val total = output.sumOf { it.first * it.second }
  println("Total: $total")
  println("Elapsed time: ${Utils.getElapsedTime()}")
}

fun parseInput(input: String): List<Pair<Int, Int>> {
  val multiples = mutableListOf<Pair<Int, Int>>();
  val matcher = Regex("""mul\((\d{1,3}),(\d{1,3})\)""").toPattern().matcher(input)
  while (matcher.find()) {
    multiples.add(Pair(matcher.group(1).toInt(), matcher.group(2).toInt()))
  }
  return multiples
}