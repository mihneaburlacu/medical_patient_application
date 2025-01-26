package com.example.new_medical_application.presentation.physiologicaldata

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.new_medical_application.business.model.MedicalData
import com.example.new_medical_application.domain.usecases.IMedicalDataUseCase
import com.example.new_medical_application.domain.usecases.IPatientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhysiologicalDataViewModel @Inject constructor(
    private val medicalDataUseCase: IMedicalDataUseCase,
    private val patientUseCase: IPatientUseCase
) : ViewModel() {
    private val _spinnerItems = MutableStateFlow<List<String>>(emptyList())
    val spinnerItems: StateFlow<List<String>> = _spinnerItems

    private val _chartData = MutableStateFlow<List<Pair<Float, String>>>(emptyList())
    val chartData: StateFlow<List<Pair<Float, String>>> = _chartData

    private var medicalDataList: List<MedicalData> = emptyList()

    init {
        loadSpinnerItems()
        getAllMedicalData()
    }

    fun showAllMedicalData() {
        viewModelScope.launch(Dispatchers.IO) {
            medicalDataUseCase.getAll().collect() {
                if (it.isEmpty()) {
                    Log.d("Mihnea123", "Medical Data list is empty")
                }

                it.forEach { data ->
                    Log.d("Mihnea123", data.toString())
                }
            }
        }
    }

    fun updateChartData(selectedParameter: String) {
        val filteredData = when (selectedParameter) {
            "Heart rate variability" -> medicalDataList.map { it.hrv.toFloat() to it.date.toString() }
            "Systolic blood pressure" -> medicalDataList.map { it.sbp.toFloat() to it.date.toString() }
            "Diastolic blood pressure" -> medicalDataList.map { it.dbp.toFloat() to it.date.toString() }
            "Oxygen saturation" -> medicalDataList.map { it.sp02.toFloat() to it.date.toString() }
            "Temperature" -> medicalDataList.map { it.temperature.toFloat() to it.date.toString() }
            "Glucose" -> medicalDataList.map { it.glucose.toFloat() to it.date.toString() }
            else -> emptyList()
        }
        _chartData.value = filteredData
    }

    private fun getAllMedicalData() {
        viewModelScope.launch(Dispatchers.IO) {
            patientUseCase.getSavedPatientSharedPreference().collect {
                if (it != null) {
                    medicalDataUseCase.getMedicalDataDetailsByPatientID(it.id).collect { data ->
                        medicalDataList = data
                        if (data.isEmpty()) {
                            Log.d("Mihnea123", "Medical Data list is empty")
                        }
                        updateChartData("Heart rate variability")
                    }
                }
            }
        }
    }

    private fun loadSpinnerItems() {
        _spinnerItems.value = listOf(
            "Heart rate variability",
            "Systolic blood pressure",
            "Diastolic blood pressure",
            "Oxygen saturation",
            "Temperature",
            "Glucose"
        )
    }
}