package com.example.new_medical_application.presentation.emergencycontacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.new_medical_application.business.model.EmergencyContact
import com.example.new_medical_application.domain.usecases.IEmergencyContactUseCase
import com.example.new_medical_application.domain.usecases.IPatientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmergencyContactsViewModel @Inject constructor(
    private val emergencyContactUseCase: IEmergencyContactUseCase,
    private val patientUseCase: IPatientUseCase
) : ViewModel() {
    private val _emergencyContactsList = MutableStateFlow<List<EmergencyContact>>(emptyList())
    val emergencyContactsList: StateFlow<List<EmergencyContact>> = _emergencyContactsList

    private val _patientID = MutableStateFlow<Long>(-1)
    val patientID: StateFlow<Long> = _patientID

    init {
        getPatientId()
        fetchContacts()
    }

    private fun fetchContacts() {
        viewModelScope.launch {
            val id = patientID.value.takeIf { it != -1L } ?: 1
            emergencyContactUseCase.getByPatientId(id)
                .collect { contacts ->
                    _emergencyContactsList.value = contacts
                }
        }
    }

    fun deleteEmergencyContact(id: Long) {
        viewModelScope.launch {
            emergencyContactUseCase.deleteById(id).collect {}
            fetchContacts()
        }
    }

    fun addEmergencyContact(contact: EmergencyContact) {
        viewModelScope.launch {
            contact.patientId = patientID.value.takeIf { it != -1L } ?: 1
            emergencyContactUseCase.insertContact(contact)
                .collect {
                    fetchContacts()
                }
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