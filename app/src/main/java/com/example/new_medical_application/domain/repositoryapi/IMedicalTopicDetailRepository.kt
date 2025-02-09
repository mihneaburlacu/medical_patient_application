package com.example.new_medical_application.domain.repositoryapi

import com.example.new_medical_application.business.model.MedicalTopicDetail

interface IMedicalTopicDetailRepository {
    suspend fun getMedicalTopic(topicId: String): MedicalTopicDetail
}