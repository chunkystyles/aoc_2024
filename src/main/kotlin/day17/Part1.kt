package day17

import utils.*
import kotlin.math.pow

val registers = mutableMapOf<String, Long>()

fun main() {
  setInputIoStartTime()
  val input = getInput(17)
  setInputParseStartTime()
  val instructions = parseInput(input)
  setAlgorithmStartTime()
  println(runProgram(instructions).joinToString(","))
  println(getElapsedTime())
}

fun runProgram(instructions: List<Pair<Int, Int>>): List<Int> {
  var stepCounter = 0
  val outputs = mutableListOf<Int>()
  while (true) {
    if (stepCounter !in instructions.indices) {
      break
    }
    var advance = true
    val (instruction, operand) = instructions[stepCounter]
    when (instruction) {
      0 -> { //adv
        registers["A"] = registers["A"]!! / 2.0.pow(getComboOperand(operand).toDouble()).toInt()
      }
      1 -> { //bxl
        registers["B"] = registers["B"]!! xor operand.toLong()
      }
      2 -> { //bst
        registers["B"] = (getComboOperand(operand) % 8).toLong()
      }
      3 -> { //jnz
        if (registers["A"] != 0L) {
          advance = false
          stepCounter = operand
        }
      }
      4 -> { //bxc
        registers["B"] = registers["B"]!! xor registers["C"]!!
      }
      5 -> { //out
        outputs.add((getComboOperand(operand) % 8).toInt())
      }
      6 -> { //bdv
//        registers["B"] = registers["A"]!! / 2.0.pow(getComboOperand(operand).toDouble()).toInt()
        throw Exception("apparently it's never used")
      }
      7 -> { //cdv
        registers["C"] = registers["A"]!! / 2.0.pow(getComboOperand(operand).toDouble()).toInt()
      }
      else -> throw Exception("Invalid instruction: $instruction")
    }
    if (advance) {
      stepCounter++
    }
  }
  return outputs
}

fun getComboOperand(operand: Int): Long {
  return when (operand) {
    1,2,3 -> operand.toLong()
    4 -> registers["A"]!!
    5 -> registers["B"]!!
    6 -> registers["C"]!!
    else -> throw Exception("Invalid operand: $operand")
  }
}

fun parseInput(input: String): List<Pair<Int, Int>> {
  val instructions = mutableListOf<Pair<Int, Int>>()
  input.lines().filter { it.isNotEmpty() }.forEach {
    if (it.startsWith("Register")) {
      registers[it.split(" ")[1].substring(0, 1)] = it.split(": ")[1].toLong()
    } else {
      val numbers = it.split(": ")[1].split(",")
      for (i in numbers.indices step 2) {
        instructions.add(numbers[i].toInt() to numbers[i + 1].toInt())
      }
    }
  }
  return instructions
}

fun updateRegisters(a: Long, b: Int, c: Int) {
  registers["A"] = a
  registers["B"] = b.toLong()
  registers["C"] = c.toLong()
}