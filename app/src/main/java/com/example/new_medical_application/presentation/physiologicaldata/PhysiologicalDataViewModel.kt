package com.example.new_medical_application.presentation.physiologicaldata

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.new_medical_application.domain.usecases.IMedicalDataUseCase
import com.example.new_medical_application.domain.usecases.MedicalDataUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhysiologicalDataViewModel @Inject constructor(
    private val medicalDataUseCase: IMedicalDataUseCase
) : ViewModel() {
    fun getAllMedicalData() {
        viewModelScope.launch(Dispatchers.IO) {
            medicalDataUseCase.getAll().collect() {
                if(it.isEmpty()) {
                    Log.d("Mihnea123", "Medical Data list is empty")
                }

                it.forEach {data ->
                    Log.d("Mihnea123", data.toString())
                }
            }
        }
    }
}