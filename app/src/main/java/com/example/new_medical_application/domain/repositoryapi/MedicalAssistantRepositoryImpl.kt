package com.example.new_medical_application.domain.repositoryapi

import com.example.new_medical_application.business.model.MedicalResponse
import com.example.new_medical_application.data.api.MedicalApiClient
import com.example.new_medical_application.data.api.model.AskMedicalRequest
import com.example.new_medical_application.data.mapper.toMedicalResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MedicalAssistantRepositoryImpl : IMedicalAssistantRepository {
    override suspend fun askMedical(instruction: String): MedicalResponse {
        return withContext(Dispatchers.IO) {
            val response = MedicalApiClient.medicalApiService.askMedical(AskMedicalRequest(instruction)).execute()
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.toMedicalResponse()
            } else {
                throw Exception("Error: ${response.errorBody()?.string()}")
            }
        }
    }
}