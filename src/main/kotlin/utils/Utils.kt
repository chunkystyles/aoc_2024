package utils

import java.net.HttpURLConnection
import java.net.URI
import java.nio.file.Paths

private const val INPUT_URL_TEMPLATE = "https://adventofcode.com/2024/day/{day}/input"
private var inputIoStartTime = 0L
private var inputParseStartTime = 0L
private var algorithmStartTime = 0L

/**
 * Retrieves the puzzle input for a given day from a local file, and if not present, then from the Advent of Code website.
 *
 * @param day The day of the Advent of Code challenge.
 * @return The puzzle input as a String.
 * @throws Exception If there is an error reading from the file and also fetching from the website.
 */
fun getInput(day: Int): String {
  val dayString = day.toString()
  val fileName = "input_${dayString.padStart(2, '0')}.txt"
  return try {
    readFileText(fileName)
  } catch (e: Exception) {
    val input = getInputFromSite(dayString)
    writeTextToFile(fileName, input)
    input
  }
}

/**
 * Deprecated. Use [setInputIoStartTime] instead.
 */
fun setStartTime() {
  inputIoStartTime = System.currentTimeMillis()
}

/**
 * Sets the start time for the file IO phase.
 */
fun setInputIoStartTime() {
  inputIoStartTime = System.currentTimeMillis()
}

/**
 * Sets the start time for the input parsing phase.
 */
fun setInputParseStartTime() {
  inputParseStartTime = System.currentTimeMillis()
}

/**
 * Sets the start time for the algorithm phase.
 */
fun setAlgorithmStartTime() {
  algorithmStartTime = System.currentTimeMillis()
}

/**
 * Calculates run time of each phase of the program.
 *
 * @return The formatted run time of each phase of the program.
 */
fun getElapsedTime(): String {
  val now = System.currentTimeMillis()
  return if (inputParseStartTime == 0L || algorithmStartTime == 0L) {
    "Elapsed time: ${getElapsedTimeBetween(inputIoStartTime, now)}"
  } else {
    "Input IO: ${getElapsedTimeBetween(inputIoStartTime, inputParseStartTime)}\n" +
        "Input Parse: ${getElapsedTimeBetween(inputParseStartTime, algorithmStartTime)}\n" +
        "Algorithm: ${getElapsedTimeBetween(algorithmStartTime, now)}\n" +
        "Total: ${getElapsedTimeBetween(inputIoStartTime, now)}"
  }
}

private fun getElapsedTimeBetween(start: Long, end: Long): String {
  val elapsedTime = end - start
  val minutes = elapsedTime / 60000
  val seconds = (elapsedTime % 60000) / 1000
  val milliseconds = elapsedTime % 1000
  return "${minutes}m ${seconds}s ${milliseconds}ms"
}

private fun getInputFromSite(day: String): String {
  val uri = URI(INPUT_URL_TEMPLATE.replace("{day}", day))
  val connection = uri.toURL().openConnection() as HttpURLConnection
  connection.requestMethod = "GET"
  connection.setRequestProperty("Cookie", readFileText("cookie"))
  connection.connect()
  return connection.inputStream.bufferedReader().use { it.readText() }
}

fun readFileText(fileName: String): String {
  return Paths.get(fileName).toFile().readText()
}

fun writeTextToFile(fileName: String, text: String) {
  Paths.get(fileName).toFile().writeText(text)
}