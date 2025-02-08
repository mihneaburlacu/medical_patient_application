package com.example.new_medical_application.data.api

import com.example.new_medical_application.common.ApiConfigConstants.ACCESS_TOKEN
import com.example.new_medical_application.common.ApiConfigConstants.FITBIT_BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FitbitApiClient {
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(ACCESS_TOKEN))
        .build()

    val fitbitRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(FITBIT_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val fitbitApiService = fitbitRetrofit.create(FitbitApiService::class.java)
}