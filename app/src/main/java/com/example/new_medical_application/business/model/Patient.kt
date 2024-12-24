package com.example.new_medical_application.business.model

data class Patient(
    val id: Long,
    var username: String = "",
    var password: String = "",
    var email: String = "",
    var name: String = "",
    var phoneNumber: String = "0727455324",
    var fitbitID: String = ""
)