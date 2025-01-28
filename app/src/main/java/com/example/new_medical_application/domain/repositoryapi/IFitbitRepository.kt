package com.example.new_medical_application.domain.repositoryapi

import com.example.new_medical_application.business.model.FitbitInfo

interface IFitbitRepository {
    suspend fun getFitbitInfo(): FitbitInfo
}