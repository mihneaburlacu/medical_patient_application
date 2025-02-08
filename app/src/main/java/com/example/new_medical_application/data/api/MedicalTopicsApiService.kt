package com.example.new_medical_application.data.api

import retrofit2.Call
import retrofit2.http.GET

interface MedicalTopicsApiService {
    @GET("myhealthfinder/api/v3/itemlist.json?Type=topic")
    fun getMedicalTopics(): Call<MedicalTopicsApiResponse>
}