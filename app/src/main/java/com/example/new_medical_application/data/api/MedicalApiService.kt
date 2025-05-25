package com.example.new_medical_application.data.api

import com.example.new_medical_application.data.api.model.AskMedicalRequest
import com.example.new_medical_application.data.api.model.MedicalResponseApiModel
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call

interface MedicalApiService {
    @POST("ask_medical")
    fun askMedical(@Body request: AskMedicalRequest): Call<MedicalResponseApiModel>
}