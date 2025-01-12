package com.example.new_medical_application.presentation.dailyvalues

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
class EnterDailyValuesViewModel @Inject constructor(
    private val medicalDataUseCase: IMedicalDataUseCase,
    private val patientUseCase: IPatientUseCase
) : ViewModel() {
    private val _insertionResult = MutableStateFlow<Boolean?>(null)
    val insertionResult: StateFlow<Boolean?> get() = _insertionResult
    private var _patientID = MutableStateFlow<Long>(-1)
    val patientID: StateFlow<Long> = _patientID

    init {
        getPatientId()
    }

    fun insertMedicalData(medicalData: MedicalData) {
        viewModelScope.launch {
            medicalDataUseCase.insertMedicalData(medicalData)
                .collect { id ->
                    _insertionResult.value = id > 0
                }
        }
    }

    fun deleteAllMedicalData() {
        viewModelScope.launch {
            medicalDataUseCase.deleteAll()
        }
    }

    private fun getPatientId() {
        viewModelScope.launch(Dispatchers.IO) {
            patientUseCase.getSavedPatientSharedPreference().collect {
                if (it != null) {
                    _patientID.value = it.id
                }
            }
        }
    }
}