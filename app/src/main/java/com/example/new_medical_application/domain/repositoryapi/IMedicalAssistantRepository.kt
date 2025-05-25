package com.example.new_medical_application.domain.repositoryapi

import com.example.new_medical_application.business.model.MedicalResponse

interface IMedicalAssistantRepository {
    suspend fun askMedical(instruction: String): MedicalResponse
}