package com.example.new_medical_application.data.database.repos

import com.example.new_medical_application.business.model.Patient
import com.example.new_medical_application.data.database.model.PatientDao
import com.example.new_medical_application.data.mapper.toDomain
import com.example.new_medical_application.data.mapper.toEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PatientRepositoryImpl @Inject constructor(
    private var patientDao: PatientDao
) : IPatientRepository {
    override fun insertPatient(patient: Patient): Long {
        return patientDao.insertPatient(patient.toEntity())
    }

    override fun insertPatientList(patients: List<Patient>): List<Long> {
        return patientDao.insertPatientAll(
            patients.map {
                it.toEntity()
            })
    }

    override fun login(username: String, password: String): Patient {
        return patientDao.readLoginData(username, password).toDomain()
    }

    override fun getPatient(id: Long): Patient {
        return patientDao.getPatientDataDetailsById(id).toDomain()
    }

    override fun getPatientByUsername(username: String): List<Patient> {
        return patientDao.getPatientDataDetailsByUsername(username).map {
            it.toDomain()
        }
    }

    override fun getAllPatient(): List<Patient> {
        return patientDao.getAllPatients().map {
            it.toDomain()
        }
    }

    override fun deleteAllPatients() {
        patientDao.getAllPatients()
    }

}