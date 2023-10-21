package com.example.stressmeter.ui.results

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.stressmeter.data.StressData
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
        initTable()
        return root
    }

    private fun initChartData(): List<Int> {
        val lines = CsvFileManager.readCSV()
        val stressData = StressData.createFromCSV(lines)
        return stressData.sortedBy { it.getTimestamp() }.map { it.getScore() }
    }

    private fun initChart() {
        val chartData = initChartData()
        val aaChartView = binding.chartResults
        val aaChartModel =
            AAChartModel().chartType(AAChartType.Area).dataLabelsEnabled(true)
                .yAxisTitle("Stress Score").zoomType(AAChartZoomType.XY).backgroundColor("#fff")
                .title("Your Stress Levels").subtitle("Stress Score / Instance").series(
                    arrayOf(
                        AASeriesElement().name("Score").data(chartData.toTypedArray())
                    )
                )

        aaChartView.aa_drawChartWithChartModel(aaChartModel)
    }

    private fun initTable() {
        val lines = CsvFileManager.readCSV()
        val stressData = StressData.createFromCSV(lines)

        fun createTableCell(content: Any): TextView {
            val textView = TextView(requireContext())
            textView.text = content.toString()
            textView.setPadding(5, 5, 5, 5)
            return textView
        }

        fun styleCell(view: View) {
            val border = GradientDrawable()
            border.setColor(Color.WHITE)
            border.setStroke(1, Color.BLACK)
            border.setSize(1, TableRow.LayoutParams.MATCH_PARENT)
            view.background = border
        }


        val table = binding.resultsTableLayout

        stressData.forEach { data ->
            val row = TableRow(requireContext())
            val lp = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT)
            row.layoutParams = lp
            val timestamp = data.getTimestamp()
            val score = data.getScore()
            val c1 = createTableCell(timestamp)
            val c2 = createTableCell(score)

            styleCell(c1)
            styleCell(c2)

            row.addView(c1)
            row.addView(c2)

            c2.textAlignment = View.TEXT_ALIGNMENT_CENTER

            table.addView(row)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}