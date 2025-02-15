package com.example.new_medical_application.data.database.repos

import com.example.new_medical_application.business.model.EmergencyContact
import com.example.new_medical_application.data.database.model.EmergencyContactDao
import com.example.new_medical_application.data.mapper.toDomain
import com.example.new_medical_application.data.mapper.toEntity
import javax.inject.Inject

class EmergencyContactRepositoryImpl @Inject constructor(
    private var emergencyContactDao: EmergencyContactDao
) : IEmergencyContactRepository {
    override suspend fun insertContact(emergencyContact: EmergencyContact): Long {
        return emergencyContactDao.insertContact(emergencyContact.toEntity())
    }

    override suspend fun getAll(): List<EmergencyContact> {
        return emergencyContactDao.getAll().map {
            it.toDomain()
        }
    }

    override suspend fun getContactByPatientId(patientId: Long): List<EmergencyContact> {
        return emergencyContactDao.getByPatientId(patientId).map {
            it.toDomain()
        }
    }

    override suspend fun getContactById(id: Long): EmergencyContact {
        return emergencyContactDao.getContactById(id).toDomain()
    }

    override suspend fun deleteById(id: Long) {
        emergencyContactDao.deleteContactById(id)
    }
}