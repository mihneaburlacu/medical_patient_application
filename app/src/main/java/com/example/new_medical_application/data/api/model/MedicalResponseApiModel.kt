package com.example.new_medical_application.data.api.model

import com.google.gson.annotations.SerializedName

data class MedicalResponseApiModel(
    @SerializedName("response")
    val response: String
)