package com.example.new_medical_application.data.api

import com.example.new_medical_application.common.ApiConfigConstants.HEALTH_BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MedicalTopicsApiClient {

    private val okHttpClient = OkHttpClient.Builder().build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(HEALTH_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val medicalTopicsApiService: MedicalTopicsApiService =
        retrofit.create(MedicalTopicsApiService::class.java)
}