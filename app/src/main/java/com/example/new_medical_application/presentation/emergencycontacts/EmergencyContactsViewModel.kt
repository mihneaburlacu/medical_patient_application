package com.example.new_medical_application.presentation.emergencycontacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.new_medical_application.business.model.EmergencyContact
import com.example.new_medical_application.domain.usecases.IEmergencyContactUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmergencyContactsViewModel @Inject constructor(
    private val emergencyContactUseCase: IEmergencyContactUseCase
) : ViewModel() {
    private val _emergencyContactsList = MutableStateFlow<List<EmergencyContact>>(emptyList())
    val emergencyContactsList: StateFlow<List<EmergencyContact>> = _emergencyContactsList

    init {
        fetchContacts()
    }

    private fun fetchContacts() {
        viewModelScope.launch {
            emergencyContactUseCase.getByPatientId(1)
                .collect { contacts ->
                    _emergencyContactsList.value = contacts
                }
        }
    }

    fun deleteEmergencyContact(id: Long) {
        viewModelScope.launch {
            emergencyContactUseCase.deleteById(id)
            fetchContacts()
        }
    }

    fun addEmergencyContact(contact: EmergencyContact) {
        viewModelScope.launch {
            emergencyContactUseCase.insertContact(contact)
                .collect {
                    fetchContacts()
                }
        }
    }
}