package com.example.new_medical_application.domain.usecases

import com.example.new_medical_application.business.model.Patient
import com.example.new_medical_application.data.database.repos.IPatientRepository
import com.example.new_medical_application.data.local.SharedPreferencesHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PatientUseCaseImpl @Inject constructor(
    private val patientRepository: IPatientRepository,
    private val sharedPreferencesHelper: SharedPreferencesHelper
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

    override fun getPatientByUsername(username: String): Flow<List<Patient>> = flow {
        emit(patientRepository.getPatientByUsername(username))
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

    override fun savePatientSharedPreference(patient: Patient) {
        sharedPreferencesHelper.savePatient(patient)
    }

    override fun getSavedPatientSharedPreference(): Flow<Patient?> = flow {
        emit(sharedPreferencesHelper.getPatient())
    }

    override fun saveWelcomeState(isShown: Boolean) {
        sharedPreferencesHelper.setWelcomeMessageFlag(isShown)
    }

    override fun getWelcomeState(): Flow<Boolean> = flow {
        emit(sharedPreferencesHelper.getWelcomeMessageFlag())
    }

    override fun clearWelcomeState() {
        sharedPreferencesHelper.clearWelcomeMessageFlag()
    }

    override fun saveRememberMeState(isChecked: Boolean) {
        sharedPreferencesHelper.setRememberMeFlag(isChecked)
    }

    override fun getRememberMeState(): Flow<Boolean> = flow {
        emit(sharedPreferencesHelper.getRememberMeFlag())
    }
}