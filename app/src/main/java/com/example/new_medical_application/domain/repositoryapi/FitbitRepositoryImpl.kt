package com.example.new_medical_application.domain.repositoryapi

import android.util.Log
import com.example.new_medical_application.business.model.FitbitInfo
import com.example.new_medical_application.data.api.FitbitApiClient.fitbitApiService
import com.example.new_medical_application.data.mapper.toFitbitInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FitbitRepositoryImpl : IFitbitRepository {
    override suspend fun getFitbitInfo(): FitbitInfo {
        return withContext(Dispatchers.IO) {
            val response = fitbitApiService.getFitbitInfo().execute()
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.toFitbitInfo()
            } else {
                throw Exception("Error fetching data: ${response.errorBody()?.string()}")
            }
        }
    }
}