package com.example.new_medical_application.data.api.model

import com.example.new_medical_application.data.api.Sections
import com.google.gson.annotations.SerializedName

data class ResourceItemApiModel(
    @SerializedName("Title") val title: String,
    @SerializedName("Sections") val section: Sections
)