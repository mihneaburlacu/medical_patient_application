package com.example.new_medical_application.data.database.repos

import com.example.new_medical_application.business.model.MedicalData

interface IMedicalDataRepository {
    fun insertMedicalData(medicalData: MedicalData): Long

    fun insertMedicalDataList(list: List<MedicalData>): List<Long>

    fun getAll(): List<MedicalData>

    fun getMedicalData(id: Long): MedicalData

    fun getMedicalDataDetailsByPatientID(patientID: Long): List<MedicalData>

    fun getMedicalDataDetailsByPatientIDAndDate(
        patientID: Long,
        date: Long
    ): List<MedicalData>

    fun deleteAll()
}