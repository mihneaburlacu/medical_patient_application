package com.example.new_medical_application.business.model

data class FitbitInfo(
    val fullName: String,
    val age: Int,
    val dateOfBirth: String,
    val encodedId: String,
    val gender: String,
    val memberSince: String,
    val height: Double,
    val weight: Double
)