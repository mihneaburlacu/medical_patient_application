package com.example.new_medical_application.domain.usecases

import com.example.new_medical_application.business.model.MedicalData
import kotlinx.coroutines.flow.Flow

interface IMedicalDataUseCase {
    fun insertMedicalData(medicalData: MedicalData): Flow<Long>

    fun insertMedicalDataList(list: List<MedicalData>): Flow<List<Long>>

    fun getAll(): Flow<List<MedicalData>>

    fun getMedicalData(id: Long): Flow<MedicalData>

    fun getMedicalDataDetailsByPatientID(patientID: Long): Flow<List<MedicalData>>

    fun getMedicalDataDetailsByPatientIDAndDate(
        patientID: Long,
        date: Long
    ): Flow<List<MedicalData>>

    fun deleteAll(): Flow<Unit>
}