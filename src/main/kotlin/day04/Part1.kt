package day04

import utils.Utils

val sequence = charArrayOf('X', 'M', 'A', 'S')

fun main() {
  Utils.setStartTime()
  val input = Utils.getInput(4)
  val wordSearch = parseInput(input);
  val total = countXmas(wordSearch)
  println("Total: $total")
  println("Elapsed time: ${Utils.getElapsedTime()}")
}

fun parseInput(input: String): List<List<Char>> {
  return input.split("\n").filter{ it.isNotBlank() }.map { it.toList() }
}

fun countXmas(wordSearch: List<List<Char>>): Int {
  var count = 0
  for (y1 in wordSearch.indices) {
    for (x1 in wordSearch[y1].indices) {
      if (characterMatchesSequence(wordSearch, y1, x1, 0)) {
        for (y2 in -1..1) {
          for (x2 in -1..1) {
            if (followLine(wordSearch, y1 + y2, x1 + x2, y2, x2, 1)) {
              count++
            }
          }
        }
      }
    }
  }
  return count
}

fun followLine(wordSearch: List<List<Char>>, y: Int, x: Int, yIncrement: Int, xIncrement: Int, sequenceNumber: Int): Boolean {
  return if (characterMatchesSequence(wordSearch, y, x, sequenceNumber)) {
    if (sequenceNumber >= sequence.size - 1) {
      true
    } else {
      followLine(wordSearch, y + yIncrement, x + xIncrement, yIncrement, xIncrement, sequenceNumber + 1)
    }
  } else {
    false
  }
}

fun characterMatchesSequence(wordSearch: List<List<Char>>, y: Int, x: Int, sequenceNumber: Int): Boolean {
  return y >= 0 && y < wordSearch.size && x >= 0 && x < wordSearch[y].size && wordSearch[y][x] == sequence[sequenceNumber]
}