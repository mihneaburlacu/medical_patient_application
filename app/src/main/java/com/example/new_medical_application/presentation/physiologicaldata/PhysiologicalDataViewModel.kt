package com.example.new_medical_application.presentation.physiologicaldata

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.new_medical_application.domain.usecases.IMedicalDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhysiologicalDataViewModel @Inject constructor(
    private val medicalDataUseCase: IMedicalDataUseCase
) : ViewModel() {
    private val _spinnerItems = MutableStateFlow<List<String>>(emptyList())
    val spinnerItems: StateFlow<List<String>> = _spinnerItems

    init {
        loadSpinnerItems()
    }
    fun getAllMedicalData() {
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