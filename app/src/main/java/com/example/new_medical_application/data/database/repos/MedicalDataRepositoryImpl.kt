package com.example.new_medical_application.data.database.repos

import com.example.new_medical_application.business.model.MedicalData
import com.example.new_medical_application.data.database.model.MedicalDataDao
import com.example.new_medical_application.data.mapper.toDomain
import com.example.new_medical_application.data.mapper.toEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicalDataRepositoryImpl @Inject constructor(
    private var medicalDataDao: MedicalDataDao
) : IMedicalDataRepository {
    override fun insertMedicalData(medicalData: MedicalData): Long {
        return medicalDataDao.insertMedicalData(medicalData.toEntity())
    }

    override fun insertMedicalDataList(list: List<MedicalData>): List<Long> {
        return medicalDataDao.insertMedicalDataAll(
            list.map {
                it.toEntity()
            }
        )
    }

    override fun getAll(): List<MedicalData> {
        return medicalDataDao.getAll().map {
            it.toDomain()
        }
    }

    override fun getMedicalData(id: Long): MedicalData {
        return medicalDataDao.getMedicalDataDetailsByID(id).toDomain()
    }

    override fun getMedicalDataDetailsByPatientID(patientID: Long): List<MedicalData> {
        return medicalDataDao.getMedicalDataDetailsByPatientID(patientID).map {
            it.toDomain()
        }
    }

    override fun getMedicalDataDetailsByPatientIDAndDate(
        patientID: Long,
        date: Long
    ): List<MedicalData> {
        return medicalDataDao.getMedicalDataDetailsByPatientIDAndDate(patientID, date).map {
            it.toDomain()
        }
    }

    override fun deleteAll() {
        medicalDataDao.deleteAll()
    }
}