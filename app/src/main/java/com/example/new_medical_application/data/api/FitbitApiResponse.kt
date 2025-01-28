package com.example.new_medical_application.data.api

import com.example.new_medical_application.data.api.model.UserApiModel
import com.google.gson.annotations.SerializedName

data class FitbitApiResponse(
    @SerializedName("user")
    val user: UserApiModel
)
