package com.example.stressmeter.managers

import android.content.Context
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class CsvFileManager {
    companion object {
        private const val csvFileName = "stress_timestamp.csv"
        private lateinit var _filesPath: String
        fun init(context: Context) {
            _filesPath = File(context.filesDir, csvFileName).absolutePath
        }

        fun createOrUpdateCSV(data: List<String>) {
            println(_filesPath)
            try {
                val file = File(_filesPath)
                val writer =
                    BufferedWriter(FileWriter(file, true))

                for (line in data) {
                    writer.write(line)
                    writer.newLine()
                }
                writer.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun readCSV(): List<String> {
            println(_filesPath)
            val lines = mutableListOf<String>()
            try {
                val file = File(_filesPath)
                if (file.exists()) {
                    val reader = BufferedReader(FileReader(file))
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        lines.add(line ?: "")
                    }
                    reader.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            println(lines)
            return lines
        }
    }


}