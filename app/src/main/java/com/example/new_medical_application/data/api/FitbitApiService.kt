package com.example.new_medical_application.data.api

import retrofit2.Call
import retrofit2.http.GET

interface FitbitApiService {
    @GET("/1/user/BSYTTK/profile.json")
    fun getFitbitInfo(): Call<FitbitApiResponse>
}