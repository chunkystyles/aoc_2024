package day17

import utils.*
import kotlin.math.pow

fun main() {
  setInputIoStartTime()
  val input = getInput(17)
  setInputParseStartTime()
  val instructions = parseInput(input)
  setAlgorithmStartTime()
  val goalInstructions = instructions.map { listOf(it.first, it.second) }.flatten()
  var potentialNumbers = mutableListOf(0L)
  for (i in goalInstructions.indices.reversed()) {
    val newPotentialNumbers = mutableListOf<Long>()
    for (potentialNumber in potentialNumbers) {
      for (j in 0..7) {
        val number = j.toLong().shl(i * 3) + potentialNumber
        updateRegisters(number, 0, 0)
        val outputs = runProgram(instructions)
        if (outputs.size < goalInstructions.size) {
          continue
        }
        var match = true
        for (k in goalInstructions.size - 1 downTo i) {
          if (goalInstructions[k] != outputs[k]) {
            match = false
            break
          }
        }
        if (match) {
          newPotentialNumbers.add(number)
        }
      }
    }
    potentialNumbers = newPotentialNumbers
  }
  println(getElapsedTime())
  println(potentialNumbers[0])
}