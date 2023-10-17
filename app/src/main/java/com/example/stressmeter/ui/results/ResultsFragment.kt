package com.example.stressmeter.ui.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.stressmeter.databinding.FragmentResultsBinding
import com.example.stressmeter.managers.CsvFileManager
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartZoomType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement


class ResultsFragment : Fragment() {
    // This property is only valid between onCreateView and
    // onDestroyView.
    private var _binding: FragmentResultsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentResultsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initChart()
        return root
    }


    private fun initChart() {
        val lines = CsvFileManager.readCSV()
        val scores = lines.map {
            val (_, score, _) = it.split(',')
            score
        }
        val scoresAndOccurrencesGrouped = scores.groupBy { it }.toMutableMap()
        var chartData = emptyList<Array<Int>>().toMutableList()
        for ((key, value) in scoresAndOccurrencesGrouped) {
            val score = key.toInt()
            val occurrence = value.size
            chartData.add(arrayOf(occurrence, score))
        }
        chartData.sortBy { it[1] }
        println("chartData:")
        chartData.forEach { i ->
            i.forEach { j ->
                println(j)
            }
            println(",")
        }
        val aaChartView = binding.chartResults
        val aaChartModel =
            AAChartModel()
                .chartType(AAChartType.Line)
                .dataLabelsEnabled(true)
                .yAxisTitle("Stress Score")
                .zoomType(AAChartZoomType.XY)
                .backgroundColor("#fff")
                .title("Your Stress Levels")
                .series(
                    arrayOf(
                        AASeriesElement().name("Score").data(
                            chartData
                                .toTypedArray()
                        )
                    )
                )

        aaChartView.aa_drawChartWithChartModel(aaChartModel)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}