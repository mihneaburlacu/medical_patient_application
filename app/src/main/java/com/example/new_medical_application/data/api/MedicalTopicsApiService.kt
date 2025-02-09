package com.example.new_medical_application.data.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MedicalTopicsApiService {
    @GET("myhealthfinder/api/v3/itemlist.json?Type=topic")
    fun getMedicalTopics(): Call<MedicalTopicsApiResponse>

    @GET("myhealthfinder/api/v3/topicsearch.json")
    fun getTopic(@Query("TopicId") topicId: String): Call<TopicResponse>
}