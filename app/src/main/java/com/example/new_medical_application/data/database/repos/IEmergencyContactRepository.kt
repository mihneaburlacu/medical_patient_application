package com.example.new_medical_application.data.database.repos

import com.example.new_medical_application.business.model.EmergencyContact

interface IEmergencyContactRepository {
    suspend fun insertContact(emergencyContact: EmergencyContact): Long

    suspend fun getAll(): List<EmergencyContact>

    suspend fun getContactByPatientId(patientId: Long): List<EmergencyContact>

    suspend fun getContactById(id: Long): EmergencyContact

    suspend fun deleteById(id: Long)
}