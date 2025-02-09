package com.example.new_medical_application.domain.repositoryapi

import com.example.new_medical_application.business.model.MedicalTopicDetail
import com.example.new_medical_application.data.api.MedicalTopicsApiClient.medicalTopicsApiService
import com.example.new_medical_application.data.mapper.toDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MedicalTopicDetailRepositoryImpl : IMedicalTopicDetailRepository {

    override suspend fun getMedicalTopic(topicId: String): MedicalTopicDetail {
        return withContext(Dispatchers.IO) {
            val response = medicalTopicsApiService.getTopic(topicId).execute()
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.result.resources.resource.firstOrNull()?.toDomainModel()
                    ?: throw Exception("No topic details available")
            } else {
                throw Exception("Error fetching topics: ${response.errorBody()?.string()}")
            }
        }
    }
}
