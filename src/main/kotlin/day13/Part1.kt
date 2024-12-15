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
  var total = 0L
  for (triple in output) {
    val (a, b) = solve(triple)
    total += a * aCost + b * bCost
  }
  println(Utils.getElapsedTime())
  println("Total: $total")
}

fun solve(triple: Triple<Pair<Int, Int>, Pair<Int, Int>, Pair<Long, Long>>): Pair<Long, Long> {
  val (buttonA, buttonB, prize) = triple
  val (ax, ay) = buttonA
  val (bx, by) = buttonB
  val (px, py) = prize
  val a = (px * by - py * bx) / (ax * by - ay * bx)
  val b = (px * ay - py * ax) / (bx * ay - by * ax)
  val x = ax * a + bx * b
  val y = ay * a + by * b
  return if (x != px || y != py) {
    Pair(0, 0)
  } else {
    Pair(a, b)
  }
}

fun parseInput(input: String): List<Triple<Pair<Int, Int>, Pair<Int, Int>, Pair<Long, Long>>> {
  val output = mutableListOf<Triple<Pair<Int, Int>, Pair<Int, Int>, Pair<Long, Long>>>()
  val matcher = Regex("""Button A: X\+(\d+), Y\+(\d+)\nButton B: X\+(\d+), Y\+(\d+)\nPrize: X=(\d+), Y=(\d+)""").toPattern().matcher(input)
  while (matcher.find()) {
    output.add(Triple(
      Pair(matcher.group(1).toInt(), matcher.group(2).toInt()),
      Pair(matcher.group(3).toInt(), matcher.group(4).toInt()),
      Pair(matcher.group(5).toLong(), matcher.group(6).toLong())
    ))
  }
  return output
}