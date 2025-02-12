package com.example.new_medical_application.business.model

data class EmergencyContact(
    val id: Long,
    val name: String,
    val phoneNumber: String,
    val email: String,
    val patientId: Long
)