package com.example.new_medical_application.domain.usecases

import android.util.Log
import com.example.new_medical_application.business.model.Patient
import com.example.new_medical_application.data.database.repos.IPatientRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PatientUseCaseImpl @Inject constructor(
    private val patientRepository: IPatientRepository
) : IPatientUseCase {

    override fun insertPatient(patient: Patient): Flow<Long> = flow {
        emit(patientRepository.insertPatient(patient))
    }

    override fun insertPatientList(patients: List<Patient>): Flow<List<Long>> = flow {
        emit(patientRepository.insertPatientList(patients))
    }

    override fun login(username: String, password: String): Flow<Patient> = flow {
        emit(patientRepository.login(username, password))
    }

    override fun getPatient(id: Long): Flow<Patient> = flow {
        emit(patientRepository.getPatient(id))
    }

    override fun getAllPatients(): Flow<List<Patient>> = flow {
        emit(patientRepository.getAllPatient())
    }

    override fun deleteAllPatients(): Flow<Unit> = flow {
        patientRepository.deleteAllPatients()
        emit(Unit)
    }
}