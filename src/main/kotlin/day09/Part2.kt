package day09

import utils.Utils

fun main() {
  Utils.setInputIoStartTime()
  val input = Utils.getInput(9)
  Utils.setInputParseStartTime()
  val output = parseInput(input)
  Utils.setAlgorithmStartTime()
  val fileSystem: MutableList<Int> = mutableListOf()
  val freeSpace: MutableList<Pair<Int, Int>> = mutableListOf()
  for (fileId in output.indices) {
    val (fileBlocks, freeBlocks) = output[fileId]
    for (i in 0 until fileBlocks) {
      fileSystem.add(fileId)
    }
    if (freeBlocks > 0) {
      freeSpace.add(Pair(fileSystem.size, freeBlocks))
      for (i in 0 until freeBlocks) {
        fileSystem.add(-1)
      }
    }
  }
  var skipNumber = 0
  for (i in fileSystem.size - 1 downTo 0) {
    if (freeSpace.isEmpty() || i < freeSpace[0].first) {
      break
    }
    if (skipNumber > 0) {
      skipNumber--
      continue
    }
    if (fileSystem[i] != -1) {
      val fileId = fileSystem[i]
      var blockCount = 1
      var here = i - 1
      while (here >= 0 && fileSystem[here] == fileId) {
        blockCount++
        here--
      }
      for (j in freeSpace.indices) {
        if (freeSpace[j].second >= blockCount && freeSpace[j].first < i) {
          for (k in 0 until blockCount) {
            fileSystem[freeSpace[j].first + k] = fileId
            fileSystem[i - k] = -1
          }
          if (blockCount == freeSpace[j].second) {
            freeSpace.removeAt(j)
          } else {
            freeSpace[j] = Pair(freeSpace[j].first + blockCount, freeSpace[j].second - blockCount)
          }
          break
        }
      }
      skipNumber = blockCount - 1
    }
  }
  var total = 0L
  for (i in fileSystem.indices) {
    if (fileSystem[i] != -1) {
      total += i * fileSystem[i]
    }
  }
  println(Utils.getElapsedTime())
  println("Total: $total")
}