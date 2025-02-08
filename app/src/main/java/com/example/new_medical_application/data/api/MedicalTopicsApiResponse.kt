package com.example.new_medical_application.data.api

import com.example.new_medical_application.data.api.model.MedicalTopicApiModel
import com.google.gson.annotations.SerializedName

data class MedicalTopicsApiResponse(
    @SerializedName("Result")
    val result: ResultWrapper
)

data class ResultWrapper(
    @SerializedName("Items")
    val items: ItemsWrapper
)

data class ItemsWrapper(
    @SerializedName("Item")
    val topics: List<MedicalTopicApiModel>
)