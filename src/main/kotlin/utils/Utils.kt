package utils

import java.net.HttpURLConnection
import java.net.URI
import java.nio.file.Paths

class Utils {
  companion object {

    private const val INPUT_URL_TEMPLATE = "https://adventofcode.com/2024/day/{day}/input"
    private var startTime = 0L

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
     * Sets the start time to the current time in milliseconds.
     * Use this method before running the code to be timed, then call getElapsedTime() after the code has run.
     */
    fun setStartTime() {
      startTime = System.currentTimeMillis()
    }

    /**
     * Returns the elapsed time since the start time was set.
     *
     * @return The elapsed time in minutes, seconds, and milliseconds.
     */
    fun getElapsedTime(): String {
      val elapsedTime = System.currentTimeMillis() - startTime
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

  }
}