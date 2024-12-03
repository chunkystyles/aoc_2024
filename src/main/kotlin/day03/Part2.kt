package day03

import utils.Utils

fun main() {
  Utils.setStartTime()
  val input = Utils.getInput(3)
  val output = parseInput2(input);
  val total = output.sumOf { it.first * it.second }
  println("Total: $total")
  println("Elapsed time: ${Utils.getElapsedTime()}")
}

fun parseInput2(input: String): List<Pair<Int, Int>> {
  val multiples = mutableListOf<Pair<Int, Int>>();
  val matcher = Regex("""mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\)""").toPattern().matcher(input)
  var enabled = true
  while (matcher.find()) {
    val matchResult = matcher.toMatchResult()
    if (matchResult.group(1) != null && enabled) {
      multiples.add(Pair(matchResult.group(1).toInt(), matchResult.group(2).toInt()))
    } else {
      enabled = matchResult.group() == "do()"
    }
  }
  return multiples
}