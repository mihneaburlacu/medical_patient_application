package com.example.new_medical_application.domain.usecases

import com.example.new_medical_application.business.model.Patient
import kotlinx.coroutines.flow.Flow

interface IPatientUseCase {
    //database
    fun insertPatient(patient: Patient): Flow<Long>
    fun insertPatientList(patients: List<Patient>): Flow<List<Long>>
    fun login(username: String, password: String): Flow<Patient>
    fun getPatientByUsername(username: String): Flow<List<Patient>>
    fun getPatient(id: Long): Flow<Patient>
    fun getAllPatients(): Flow<List<Patient>>
    fun deleteAllPatients(): Flow<Unit>

    //shared preference
    fun savePatientSharedPreference(patient: Patient)
    fun getSavedPatientSharedPreference(): Flow<Patient?>
    fun saveWelcomeState(isShown: Boolean)
    fun getWelcomeState(): Flow<Boolean>
    fun clearWelcomeState()
}