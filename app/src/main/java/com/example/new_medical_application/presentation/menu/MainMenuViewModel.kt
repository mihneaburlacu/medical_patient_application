package com.example.new_medical_application.presentation.menu

import androidx.lifecycle.ViewModel
import com.example.new_medical_application.domain.usecases.IPatientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor(
    private val patientUseCase: IPatientUseCase
) : ViewModel() {
}