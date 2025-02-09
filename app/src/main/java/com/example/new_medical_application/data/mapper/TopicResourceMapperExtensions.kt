package com.example.new_medical_application.data.mapper

import com.example.new_medical_application.business.model.MedicalTopicDetail
import com.example.new_medical_application.data.api.model.ResourceItemApiModel

fun ResourceItemApiModel.toDomainModel(): MedicalTopicDetail {
    return MedicalTopicDetail(
        title = this.title,
        content = this.section.section.firstOrNull()?.content ?: "No content available"
    )
}