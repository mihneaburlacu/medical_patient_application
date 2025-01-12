package com.example.new_medical_application.domain.usecases

import com.example.new_medical_application.business.model.MedicalData
import com.example.new_medical_application.data.database.repos.IMedicalDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicalDataUseCaseImpl @Inject constructor(
    private val medicalDataRepository: IMedicalDataRepository
) : IMedicalDataUseCase {
    override fun insertMedicalData(medicalData: MedicalData): Flow<Long> = flow {
        emit(medicalDataRepository.insertMedicalData(medicalData))
    }.flowOn(Dispatchers.IO)

    override fun insertMedicalDataList(list: List<MedicalData>): Flow<List<Long>> = flow {
        emit(medicalDataRepository.insertMedicalDataList(list))
    }

    override fun getAll(): Flow<List<MedicalData>> = flow {
        emit(medicalDataRepository.getAll())
    }

    override fun getMedicalData(id: Long): Flow<MedicalData> = flow {
        emit(medicalDataRepository.getMedicalData(id))
    }

    override fun getMedicalDataDetailsByPatientID(patientID: Long): Flow<List<MedicalData>> = flow {
        emit(medicalDataRepository.getMedicalDataDetailsByPatientID(patientID))
    }

    override fun getMedicalDataDetailsByPatientIDAndDate(
        patientID: Long,
        date: Long
    ): Flow<List<MedicalData>> = flow {
        emit(medicalDataRepository.getMedicalDataDetailsByPatientIDAndDate(patientID, date))
    }

    override fun deleteAll(): Flow<Unit> = flow {
        medicalDataRepository.deleteAll()
        emit(Unit)
    }
}