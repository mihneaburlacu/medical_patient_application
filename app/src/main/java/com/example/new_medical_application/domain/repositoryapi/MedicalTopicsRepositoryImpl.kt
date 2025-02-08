package com.example.new_medical_application.domain.repositoryapi

import com.example.new_medical_application.business.model.MedicalTopic
import com.example.new_medical_application.data.api.MedicalTopicsApiClient.medicalTopicsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MedicalTopicsRepositoryImpl : IMedicalTopicsRepository {
    override suspend fun getMedicalTopics(): List<MedicalTopic> {
        return withContext(Dispatchers.IO) {
            val response = medicalTopicsApiService.getMedicalTopics().execute()
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.result.items.topics.map { apiModel ->
                    MedicalTopic(
                        id = apiModel.id,
                        title = apiModel.title
                    )
                }
            } else {
                throw Exception("Error fetching topics: ${response.errorBody()?.string()}")
            }
        }
    }
}
