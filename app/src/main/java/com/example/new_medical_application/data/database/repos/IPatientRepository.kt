package com.example.new_medical_application.data.database.repos

import com.example.new_medical_application.business.model.Patient

interface IPatientRepository {
    fun insertPatient(patient: Patient): Long

    fun insertPatientList(patients: List<Patient>): List<Long>

    fun login(username: String, password: String): Patient

    fun getPatient(id: Long): Patient

    fun getPatientByUsername(username: String): List<Patient>

    fun getAllPatient(): List<Patient>

    fun deleteAllPatients()
}