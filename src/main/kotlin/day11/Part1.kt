package day11

import utils.Utils
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

fun main() {
  Utils.setInputIoStartTime()
  val input = Utils.getInput(11)
  Utils.setInputParseStartTime()
  val output = parseInput(input)
  Utils.setAlgorithmStartTime()
  val numberOfBlinks = 25
  var stones = blink(output)
  for (i in 0 until numberOfBlinks - 1) {
    stones = blink(stones)
  }
  println(Utils.getElapsedTime())
  println(stones.size)
}

fun parseInput(input: String): List<Long> {
  return input.split("\n")
    .filter { it.isNotEmpty() }
    .map { it.split(" ") }
    .flatten()
    .map { it.toLong() }
}

fun blink(oldList: List<Long>): List<Long> {
  val newList = mutableListOf<Long>()
  for (stone in oldList) {
    if (stone == 0L) {
      newList.add(1L)
    } else {
      val digits = (floor(log10(stone.toDouble()) + 1)).toInt()
      if (digits % 2 == 0) {
        val multiplier = 10.0.pow(digits / 2).toLong()
        val right = stone % multiplier
        val left = (stone - right) / multiplier
        newList.add(left)
        newList.add(right)
      } else {
        newList.add(stone * 2024L)
      }
    }
  }
  return newList
}