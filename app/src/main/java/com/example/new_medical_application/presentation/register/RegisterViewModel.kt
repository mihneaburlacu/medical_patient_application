package com.example.new_medical_application.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.new_medical_application.business.model.Patient
import com.example.new_medical_application.domain.usecases.IPatientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val patientUseCase: IPatientUseCase
) : ViewModel() {
    private val _registrationState = MutableStateFlow<RegistrationState>(RegistrationState.Idle)
    val registrationState: StateFlow<RegistrationState> = _registrationState

    fun register(
        username: String,
        password: String,
        email: String,
        name: String,
        phoneNumber: String,
        fitbitId: String
    ) {
        if (username.isBlank() || password.isBlank() || email.isBlank() || name.isBlank() || phoneNumber.isBlank() || fitbitId.isBlank()) {
            _registrationState.value = RegistrationState.Error("All fields must be filled")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _registrationState.value = RegistrationState.Loading
            try {
                val patient = Patient(0, username, password, email, name, phoneNumber, fitbitId)
                patientUseCase.insertPatient(patient).collect {
                    patient.id = it
                }
                _registrationState.value = RegistrationState.Success(patient)
            } catch (e: Exception) {
                _registrationState.value = RegistrationState.Error("Registration failed")
            }
        }
    }

    fun resetState() {
        _registrationState.value = RegistrationState.Idle
    }

    sealed class RegistrationState {
        data object Idle : RegistrationState()
        data object Loading : RegistrationState()
        data class Success(val patient: Patient) : RegistrationState()
        data class Error(val message: String) : RegistrationState()
    }
}