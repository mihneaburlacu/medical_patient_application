package com.example.new_medical_application.data.api

import com.example.new_medical_application.data.api.model.ResourceItemApiModel
import com.google.gson.annotations.SerializedName

data class TopicResponse(
    @SerializedName("Result") val result: TopicResult
)

data class TopicResult(
    @SerializedName("Resources") val resources: TopicResources
)

data class TopicResources(
    @SerializedName("Resource") val resource: List<ResourceItemApiModel>
)

data class Sections(
    @SerializedName("section") val section: List<SectionItem>
)

data class SectionItem(
    @SerializedName("Content") val content: String
)
