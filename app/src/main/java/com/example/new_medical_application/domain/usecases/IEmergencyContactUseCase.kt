package com.example.new_medical_application.domain.usecases

import com.example.new_medical_application.business.model.EmergencyContact
import kotlinx.coroutines.flow.Flow

interface IEmergencyContactUseCase {
    fun insertContact(contact: EmergencyContact): Flow<Long>

    fun getAll(): Flow<List<EmergencyContact>>

    fun getByPatientId(patientId: Long): Flow<List<EmergencyContact>>

    fun deleteById(id: Long)
}