package com.example.new_medical_application.business.model

import java.util.Date
import java.util.UUID

data class MedicalData(
    val id: Long,
    var sp02: Int,
    var glucose: Int,
    var sbp: Int,
    var dbp: Int,
    var sleepMinutes: Int,
    var temperature: Double,
    var hrv: Double,
    var date: Date,
    var patientID: UUID
)