package com.example.new_medical_application.domain.repositoryapi

import com.example.new_medical_application.business.model.MedicalTopic

interface IMedicalTopicsRepository {
    suspend fun getMedicalTopics(): List<MedicalTopic>

}