package day12

import utils.*

fun main() {
  setInputIoStartTime()
  val input = getInput(12)
  setInputParseStartTime()
  val output = parseInput(input)
  setAlgorithmStartTime()
  val rows = output.size
  val cols = output[0].size
  val map = mutableMapOf<String, MutableList<Plot>>()
  val plots = mutableListOf<MutableList<Plot>>()
  val directions = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)
  for (y in 0 until rows) {
    val row = mutableListOf<Plot>()
    for (x in 0 until cols) {
      row.add(Plot(output[y][x], x, y, null))
    }
    plots.add(row)
  }
  for (y in 0 until rows) {
    for (x in 0 until cols) {
      val plot = plots[y][x]
      val connectedPlots = mutableListOf<Plot>()
      for ((j, i) in directions) {
        val newX = x + j
        val newY = y + i
        if (newX >= 0 && newX < output[y].size && newY >= 0 && newY < output.size && plots[newY][newX].char == plot.char) {
          connectedPlots.add(plots[newY][newX])
        }
      }
      if (connectedPlots.isNotEmpty() && connectedPlots.any { it.head != null }) { // missing condition
        val heads = connectedPlots.filter { it.head != null }.map { it.head }.distinct()
        if (heads.size > 1) {
          val head = heads[0]
          for (headIndex in 1 until heads.size) {
            val otherPlots = map[heads[headIndex]!!.getId()]
            for (otherIndex in otherPlots!!.indices) {
              val otherPlot = otherPlots[otherIndex]
              otherPlot.head = head
              map[head!!.getId()]!!.add(otherPlot)
            }
            map.remove(heads[headIndex]!!.getId())
          }
        }
        plot.head = heads[0]
        map[heads[0]!!.getId()]!!.add(plot)
      } else {
        plot.head = plot
        with(map) { put(plot.getId(), mutableListOf(plot)) }
      }
      plot.connections = connectedPlots.size
    }
  }
  var total = 0
  for (mapPlots in map.values) {
    var fences = 0
    var area = 0
    for (plot in mapPlots) {
      area++
      fences += (4 - plot.connections)
    }
    total += area * fences
  }
  println(getElapsedTime())
  println(total)
}

class Plot(val char: Char, val x: Int, val y: Int, var head: Plot?, var connections: Int = 0) {
  fun getId(): String {
    return "$y-$x"
  }
  override fun toString(): String {
    return "Plot(id: ${getId()}, head: ${head!!.getId()}, connections: $connections)"
  }
}

fun parseInput(input: String): List<List<Char>> {
  return input
    .split("\n")
    .filter { it.isNotEmpty() }
    .map { it.toList() }
}