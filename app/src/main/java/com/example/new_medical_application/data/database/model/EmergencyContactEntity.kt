package com.example.new_medical_application.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "emergency_contact")
class EmergencyContactEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    var name: String = "",
    var email: String = "",
    var phoneNumber: String = "",
    var patientId: Long
)