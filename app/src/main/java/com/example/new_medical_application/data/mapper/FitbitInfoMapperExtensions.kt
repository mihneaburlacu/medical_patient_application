package com.example.new_medical_application.data.mapper

import com.example.new_medical_application.business.model.FitbitInfo
import com.example.new_medical_application.data.api.FitbitApiResponse

fun FitbitApiResponse.toFitbitInfo(): FitbitInfo = FitbitInfo(
    fullName = this.user.fullName,
    age = this.user.age,
    dateOfBirth = this.user.dateOfBirth,
    encodedId = this.user.encodedId,
    gender = this.user.gender,
    memberSince = this.user.memberSince,
    height = this.user.height,
    weight = this.user.weight
)