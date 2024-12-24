package com.example.new_medical_application.data.mapper

import com.example.new_medical_application.business.model.Patient
import com.example.new_medical_application.data.database.model.PatientEntity

fun Patient.toEntity(): PatientEntity {
    return PatientEntity(
        id = this.id,
        username = this.username,
        password = this.password,
        email = this.email,
        name = this.name,
        phoneNumber = this.phoneNumber,
        fitbitID = this.fitbitID
    )
}

fun PatientEntity.toDomain(): Patient {
    return Patient(
        id = this.id,
        username = this.username,
        password = this.password,
        email = this.email,
        name = this.name,
        phoneNumber = this.phoneNumber,
        fitbitID = this.fitbitID
    )
}
