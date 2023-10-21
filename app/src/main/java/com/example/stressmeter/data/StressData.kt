package com.example.stressmeter.data

import java.util.Date

class StressData(
    private val _resourceId: Int,
    private val _stressScore: Int,
    private val _timeStamp: Long = Date().time
) {
    companion object {
        private fun createFromCSV(csvCol: String): StressData {
            val (resourceId, stressScore, timeStamp) = csvCol.split(",")
            return StressData(resourceId.toInt(), stressScore.toInt(), timeStamp.toLong())
        }

        fun createFromCSV(csv: List<String>): List<StressData> {
            return csv.map { createFromCSV(it) }.toList()
        }
    }

    @Suppress("unused")
    fun getResourceId(): Int {
        return _resourceId
    }

    fun getScore(): Int {
        return _stressScore
    }

    fun getTimestamp(): Long {
        return _timeStamp
    }

    override fun toString(): String {
        return "${_resourceId},${_stressScore},${_timeStamp}"
    }

    fun toCSV(): String {
        return this.toString()
    }
}