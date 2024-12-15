package day02

import utils.*
import kotlin.math.abs

fun main() {
  setStartTime()
  val input = getInput(2)
  val reports = parseInput(input)
  var count = 0
  reports.map() {
    if (isReportSafe(it)) {
      count++
    }
  }
  println("Safe levels: $count")
  println("Elapsed time: ${getElapsedTime()}")
}

fun isReportSafe(level: List<Int>): Boolean {
  val isIncreasing = level[0] < level[1]
  for (i in 0 until level.size - 1) {
    val left = level[i]
    val right = level[i + 1]
    if (left == right) {
      return false
    }
    if (isIncreasing && left >= right) {
      return false
    }
    if (!isIncreasing && left <= right) {
      return false
    }
    if (abs(left - right) > 3) {
      return false
    }
  }
  return true
}

fun parseInput(input: String): List<List<Int>> {
  return input
    .split("\n")
    .filter { it.isNotBlank() }
    .map { it ->
      it
        .split(" ")
        .map { it.toInt() }
    }
}