package com.example.new_medical_application.presentation.physiologicaldata

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.new_medical_application.R
import com.example.new_medical_application.databinding.FragmentPhysiologicalDataBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@AndroidEntryPoint
class PhysiologicalDataFragment : Fragment() {
    private var _binding: FragmentPhysiologicalDataBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PhysiologicalDataViewModel by viewModels()
    private lateinit var chart: LineChart

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.showAllMedicalData()
        collectSpinnerItems()
        initialiseChart()
        collectChartData()
        setupSpinnerListener()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhysiologicalDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initialiseChart() {
        chart = binding.chart
        chart.setTouchEnabled(true)
        chart.setPinchZoom(true)
        chart.description.isEnabled = false
        addMarkerView(requireContext())
        formatChartXAxis()
    }

    private fun formatChartXAxis() {
        val xAxis = chart.xAxis
        xAxis.granularity = 1f
        xAxis.position = com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM
    }

    private fun addMarkerView(context: Context) {
        val markerView = CustomMarkerView(context)
        markerView.chartView = chart
        chart.marker = markerView
    }

    private fun collectSpinnerItems() {
        lifecycleScope.launch {
            viewModel.spinnerItems.collect { spinnerItems ->
                setupSpinner(spinnerItems)
            }
        }
    }

    private fun setupSpinner(spinnerItems: List<String>) {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            spinnerItems
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.medicalParametersSpinner.adapter = adapter
    }

    private fun collectChartData() {
        lifecycleScope.launch {
            viewModel.chartData.collect { chartData ->
                updateChart(chartData)
            }
        }
    }

    private fun setupSpinnerListener() {
        binding.medicalParametersSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedParameter = parent?.getItemAtPosition(position).toString()
                    viewModel.updateChartData(selectedParameter)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Do nothing
                }
            }
    }

    private fun updateChart(dataPoints: List<Pair<Float, String>>) {
        val selectedParameterName = binding.medicalParametersSpinner.selectedItem.toString()
        val formattedDates = formatDates(dataPoints)
        val entries = dataPoints.mapIndexed { index, pair ->
            Entry(index.toFloat(), pair.first)
        }
        val lineDataSet = LineDataSet(entries, selectedParameterName).apply {
            color = resources.getColor(R.color.secondary_color, null)
            valueTextColor = resources.getColor(R.color.black, null)
            valueTextSize = 12f
            setDrawValues(false)
            setDrawCircles(true)
            circleColors = listOf(resources.getColor(R.color.main_color))
        }
        val baselineEntries = dataPoints.indices.map { index ->
            Entry(index.toFloat(), dataPoints.first().first)
        }
        val baselineDataSet = LineDataSet(baselineEntries, "Baseline").apply {
            color = resources.getColor(R.color.baseline_color, null)
            lineWidth = 2f
            setDrawCircles(false)
            setDrawValues(false)
        }
        chart.data = LineData(lineDataSet, baselineDataSet)
        chart.xAxis.valueFormatter = IndexAxisValueFormatter(formattedDates)
        chart.invalidate()
    }

    private fun formatDates(dataPoints: List<Pair<Float, String>>): List<String> {
        val inputDateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
        inputDateFormat.timeZone = TimeZone.getTimeZone("GMT")
        val dateFormat = SimpleDateFormat("MM-dd", Locale.ENGLISH)
        dateFormat.timeZone = TimeZone.getTimeZone("GMT")
        return dataPoints.map { dataPoint ->
            val parsedDate = inputDateFormat.parse(dataPoint.second)
            dateFormat.format(parsedDate ?: dataPoint.second)
        }
    }
}