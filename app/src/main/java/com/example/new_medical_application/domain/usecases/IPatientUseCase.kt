package com.example.new_medical_application.domain.usecases

import com.example.new_medical_application.business.model.Patient
import kotlinx.coroutines.flow.Flow

interface IPatientUseCase {
    fun insertPatient(patient: Patient): Flow<Long>
    fun insertPatientList(patients: List<Patient>): Flow<List<Long>>
    fun login(username: String, password: String): Flow<Patient>
    fun getPatient(id: Long): Flow<Patient>
    fun getAllPatients(): Flow<List<Patient>>
    fun deleteAllPatients(): Flow<Unit>
}