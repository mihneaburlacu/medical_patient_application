package com.example.new_medical_application.data.api.model

import com.google.gson.annotations.SerializedName

data class MedicalTopicApiModel(
    @SerializedName("Id")
    val id: String,

    @SerializedName("Title")
    val title: String
)
