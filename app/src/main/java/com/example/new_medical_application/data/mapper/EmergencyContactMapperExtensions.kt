package com.example.new_medical_application.data.mapper

import com.example.new_medical_application.business.model.EmergencyContact
import com.example.new_medical_application.data.database.model.EmergencyContactEntity

fun EmergencyContact.toEntity(): EmergencyContactEntity {
    return EmergencyContactEntity(
        id = this.id,
        name = this.name,
        email = this.email,
        phoneNumber = this.phoneNumber,
        patientId = this.patientId
    )
}

fun EmergencyContactEntity.toDomain(): EmergencyContact {
    return EmergencyContact(
        id = this.id,
        name = this.name,
        email = this.email,
        phoneNumber = this.phoneNumber,
        patientId = this.patientId
    )
}