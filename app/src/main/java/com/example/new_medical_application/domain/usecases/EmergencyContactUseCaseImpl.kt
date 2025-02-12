package com.example.new_medical_application.domain.usecases

import com.example.new_medical_application.business.model.EmergencyContact
import com.example.new_medical_application.data.database.repos.IEmergencyContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Singleton

@Singleton
class EmergencyContactUseCaseImpl(emergencyContactRepository: IEmergencyContactRepository) : IEmergencyContactUseCase {
    private var emergencyList = mutableListOf(
        EmergencyContact(
            1,
            "Mihnea-Sebastian Burlacu",
            "0727455324",
            "mihneasebastianburlacu@gmail.com",
            1
        ),
        EmergencyContact(2, "Roxana Dorobantu", "0727455324", "roxanadorobantu64@gmail.com", 1)
    )

    override fun insertContact(contact: EmergencyContact): Flow<Long> = flow {
        emergencyList.add(contact)
        emit(4L)
    }.flowOn(Dispatchers.IO)

    override fun getAll(): Flow<List<EmergencyContact>> = flow {
        emit(emergencyList)
    }

    override fun getByPatientId(patientId: Long): Flow<List<EmergencyContact>> = flow {
        emit(emergencyList)
    }
}