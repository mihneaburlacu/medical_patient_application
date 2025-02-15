package com.example.new_medical_application.domain.usecases

import com.example.new_medical_application.business.model.EmergencyContact
import com.example.new_medical_application.data.database.repos.IEmergencyContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmergencyContactUseCaseImpl @Inject constructor(
    private val emergencyContactRepository: IEmergencyContactRepository
) : IEmergencyContactUseCase {
    override fun insertContact(contact: EmergencyContact): Flow<Long> = flow {
        emit(emergencyContactRepository.insertContact(contact))
    }.flowOn(Dispatchers.IO)

    override fun getAll(): Flow<List<EmergencyContact>> = flow {
        emit(emergencyContactRepository.getAll())
    }

    override fun getByPatientId(patientId: Long): Flow<List<EmergencyContact>> = flow {
        emit(emergencyContactRepository.getContactByPatientId(patientId))
    }

    override fun getContact(id: Long): Flow<EmergencyContact> = flow {
        emit(emergencyContactRepository.getContactById(id))
    }

    override fun deleteById(id: Long): Flow<Unit> = flow {
        emergencyContactRepository.deleteById(id)
    }
}