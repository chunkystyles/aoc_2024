package day09

import utils.Utils

fun main() {
  Utils.setInputIoStartTime()
  val input = Utils.getInput(9)
  Utils.setInputParseStartTime()
  val output = parseInput(input)
  Utils.setAlgorithmStartTime()
  val fileSystem: MutableList<Int> = mutableListOf()
  for (fileId in output.indices) {
    val (fileBlocks, freeBlocks) = output[fileId]
    for (i in 0 until fileBlocks) {
      fileSystem.add(fileId)
    }
    for (i in 0 until freeBlocks) {
      fileSystem.add(-1)
    }
  }
  var lowerIndex = 0
  for (i in fileSystem.size - 1 downTo 0) {
    if (fileSystem[i] != -1) {
      while (fileSystem[lowerIndex] != -1) {
        lowerIndex++
      }
      if (lowerIndex >= i) {
        break
      }
      fileSystem[lowerIndex] = fileSystem[i]
      fileSystem[i] = -1
    }
  }
  var total = 0L
  for (i in fileSystem.indices) {
    if (fileSystem[i] == -1) {
      break
    }
    total += i * fileSystem[i]
  }
  println(Utils.getElapsedTime())
  println("Total: $total")
}

fun parseInput(input: String): List<Pair<Int, Int>> {
  var newInput = input.trim()
  if (newInput.length % 2 != 0) {
    newInput += "0"
  }
  val list = newInput.split("").filter { it.isNotBlank() }.map { it.toInt() }
  val pairs = MutableList(list.size / 2) { Pair(0, 0) }
  for (i in list.indices step 2) {
    pairs[i / 2] = Pair(list[i], list[i + 1])
  }
  return pairs
}