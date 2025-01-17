package com.example.new_medical_application.presentation.physiologicaldata

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

@AndroidEntryPoint
class PhysiologicalDataFragment : Fragment() {
    private var _binding: FragmentPhysiologicalDataBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PhysiologicalDataViewModel by viewModels()
    private lateinit var chart: LineChart

    //TODO Hardcoded data, remove later
    private val dataPoints = listOf(
        Pair(110f, "12.05.2025"),
        Pair(120f, "13.05.2025"),
        Pair(105f, "14.05.2025"),
        Pair(108f, "15.05.2025"),
        Pair(110f, "16.05.2025"),
        Pair(104f, "17.05.2025")
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllMedicalData()
        collectSpinnerItems()
        initialiseChart()
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
        addDataForChart()
    }

    private fun formatChartXAxis() {
        val xAxis = chart.xAxis
        xAxis.granularity = 1f
        xAxis.position = com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = IndexAxisValueFormatter(dataPoints.map { it.second })
    }

    private fun addDataForChart() {
        val entries = dataPoints.mapIndexed { index, pair ->
            Entry(index.toFloat(), pair.first)
        }
        val lineDataSet = LineDataSet(entries, "Temperature").apply {
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
        chart.invalidate()
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
}