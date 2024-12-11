package day11

import utils.Utils
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

val singleCache = mutableMapOf<Long, Pair<Long, Long>>()
val multipleCache = mutableMapOf<Pair<Long, Int>, Long>()

fun main() {
  Utils.setInputIoStartTime()
  val input = Utils.getInput(11)
  Utils.setInputParseStartTime()
  val output = parseInput(input)
  Utils.setAlgorithmStartTime()
  val numberOfBlinks = 75
  var count = 0L
  for (stone in output) {
    count += multipleBlinks(stone, numberOfBlinks)
  }
  println(Utils.getElapsedTime())
  println("count = $count")
}

fun multipleBlinks(stone: Long, blinks: Int): Long = multipleCache.getOrPut(stone to blinks) {
  singleBlink(stone).let { (left, right) ->
    if (blinks == 1) {
      if (right == -1L) 1 else 2
    } else {
      val leftBlinks = multipleBlinks(left, blinks - 1)
      if (right == -1L) {
        return leftBlinks
      } else {
        leftBlinks + multipleBlinks(right, blinks - 1)
      }
    }
  }
}

fun singleBlink(stone: Long): Pair<Long, Long> = singleCache.getOrPut(stone) {
  if (stone == 0L) {
    1L to -1L
  } else {
    val digits = (floor(log10(stone.toDouble()) + 1)).toInt()
    if (digits % 2 == 0) {
      val multiplier = 10.0.pow(digits / 2).toLong()
      val right = stone % multiplier
      val left = (stone - right) / multiplier
      left to right
    } else {
      stone * 2024L to -1
    }
  }
}