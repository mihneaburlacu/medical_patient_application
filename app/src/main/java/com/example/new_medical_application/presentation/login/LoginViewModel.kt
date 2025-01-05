package com.example.new_medical_application.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.new_medical_application.business.model.Patient
import com.example.new_medical_application.domain.usecases.IPatientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val patientUseCase: IPatientUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _loginState.value = LoginState.Error("Username and password cannot be empty")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _loginState.value = LoginState.Loading
            try {
                patientUseCase.login(username, password).collect { patient ->
                    patientUseCase.savePatientSharedPreference(patient)
                    _loginState.value = LoginState.Success(patient)
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Login failed")
            }
        }
    }

    fun resetState() {
        _loginState.value = LoginState.Idle
    }

    fun getAllPatients() {
        viewModelScope.launch(Dispatchers.IO) {
            patientUseCase.getAllPatients().collect {
                it.forEach { patient ->
                    Log.d("Mihnea123", patient.toString())
                }
            }
        }
    }

    sealed class LoginState {
        data object Idle : LoginState()
        data object Loading : LoginState()
        data class Success(val patient: Patient) : LoginState()
        data class Error(val message: String) : LoginState()
    }
}