package day04

import utils.Utils

fun main() {
  Utils.setStartTime()
  val input = Utils.getInput(4)
  val wordSearch = parseInput(input);
  val total = countXmas2(wordSearch)
  println("Total: $total")
  println("Elapsed time: ${Utils.getElapsedTime()}")
}

fun countXmas2(wordSearch: List<List<Char>>): Int {
  var count = 0
  for (y1 in wordSearch.indices) {
    for (x1 in wordSearch[y1].indices) {
      if (wordSearch[y1][x1] == 'A'
        && matchDiagonal(wordSearch, y1 - 1, x1 - 1, y1 + 1, x1 + 1)
        && matchDiagonal(wordSearch, y1 - 1, x1 + 1, y1 + 1, x1 - 1)
      ) {
        count++
      }
    }
  }
  return count
}

fun matchDiagonal(wordSearch: List<List<Char>>, y1: Int, x1: Int, y2: Int, x2: Int): Boolean {
  if (y1 < 0 || y1 >= wordSearch.size || x1 < 0 || x1 >= wordSearch[y1].size || y2 < 0 || y2 >= wordSearch.size || x2 < 0 || x2 >= wordSearch[y2].size) {
    return false
  }
  val char1 = wordSearch[y1][x1]
  val char2 = wordSearch[y2][x2]
  return when (char1) {
    'M' -> char2 == 'S'
    'S' -> char2 == 'M'
    else -> false
  }
}