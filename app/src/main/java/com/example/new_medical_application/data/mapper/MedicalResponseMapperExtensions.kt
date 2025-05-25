package com.example.new_medical_application.data.mapper

import com.example.new_medical_application.business.model.MedicalResponse
import com.example.new_medical_application.data.api.model.MedicalResponseApiModel

fun MedicalResponseApiModel.toMedicalResponse(): MedicalResponse =
    MedicalResponse(response = this.response)