package day02

import utils.*
import kotlin.math.abs

fun main() {
  setStartTime()
  val input = getInput(2)
  val reports = parseInput(input)
  var count = 0
  reports.map() {
    val first = Report(false, it)
    var checkReport = isReportIncreasing(first)
    if (!checkReport.valid) {
      checkReport = isReportDecreasing(first)
    }
    if (checkReport.valid && isReportSafe(checkReport)) {
      count++
    }
  }
  println("Safe levels: $count")
  println("Elapsed time: ${getElapsedTime()}")
}

fun isReportSafe(report: Report): Boolean {
  val levels = report.levels
  for (i in 0 until levels.size - 1) {
    val left = levels[i]
    val right = levels[i + 1]
    if (abs(left - right) > 3) {
      if (report.removals > 0) {
        return false
      }
      if (i == 0) {
        return isReportSafe(Report(false, levels.subList(1, levels.size), report.removals + 1))
      } else if (i == levels.size - 2) {
        return isReportSafe(Report(false, levels.subList(0, levels.size - 1), report.removals + 1))
      } else {
        return false
      }
    }
  }
  return true
}

class Report(var valid: Boolean, var levels: List<Int>, var removals: Int = 0) {
  override fun toString(): String {
    return "Report(valid=$valid, levels=$levels, removals=$removals)"
  }
}

fun isReportIncreasing(report: Report): Report {
  val levels = report.levels
  for (i in 0 until levels.size - 1) {
    if (levels[i] >= levels[i + 1]) {
      if (report.removals > 0) {
        return report
      } else {
        var newLevels = levels.toMutableList()
        newLevels.removeAt(i)
        val newReport = Report(false, newLevels, report.removals + 1)
        val checkReport = isReportIncreasing(newReport)
        if (checkReport.valid) {
          return newReport
        } else {
          newLevels = levels.toMutableList()
          newLevels.removeAt(i + 1)
          newReport.levels = newLevels
          return isReportIncreasing(newReport)
        }
      }
    }
  }
  report.valid = true
  return report
}

fun isReportDecreasing(report: Report): Report {
  val levels = report.levels
  for (i in 0 until levels.size - 1) {
    if (levels[i] <= levels[i + 1]) {
      if (report.removals > 0) {
        return report
      } else {
        var newLevels = levels.toMutableList()
        newLevels.removeAt(i)
        val newReport = Report(false, newLevels, report.removals + 1)
        val checkReport = isReportDecreasing(newReport)
        if (checkReport.valid) {
          return newReport
        } else {
          newLevels = levels.toMutableList()
          newLevels.removeAt(i + 1)
          newReport.levels = newLevels
          return isReportDecreasing(newReport)
        }
      }
    }
  }
  report.valid = true
  return report
}