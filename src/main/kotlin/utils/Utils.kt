package utils

import java.net.HttpURLConnection
import java.net.URI
import java.nio.file.Paths

class Utils {
    companion object {

        private const val INPUT_URL_TEMPLATE = "https://adventofcode.com/2024/day/{day}/input"

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

        private fun getInputFromSite(day: String): String {
            val uri = URI(INPUT_URL_TEMPLATE.replace("{day}", day))
            val connection = uri.toURL().openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("Cookie", readFileText("cookie"))
            connection.connect()
            return connection.inputStream.bufferedReader().use { it.readText() }
        }

        private fun readFileText(fileName: String): String {
            return Paths.get(fileName).toFile().readText()
        }

        private fun writeTextToFile(fileName: String, text: String) {
            Paths.get(fileName).toFile().writeText(text)
        }

    }
}