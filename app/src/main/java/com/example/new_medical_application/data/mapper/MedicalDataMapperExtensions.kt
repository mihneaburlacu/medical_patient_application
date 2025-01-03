package com.example.new_medical_application.data.mapper

import com.example.new_medical_application.business.model.MedicalData
import com.example.new_medical_application.data.database.model.MedicalDataEntity

fun MedicalData.toEntity(): MedicalDataEntity {
    return MedicalDataEntity(
        id = this.id,
        sp02 = this.sp02,
        glucose = this.glucose,
        sbp = this.sbp,
        dbp = this.dbp,
        temperature = this.temperature,
        hrv = this.hrv,
        date = this.date,
        patientID = this.patientID
    )
}

fun MedicalDataEntity.toDomain(): MedicalData {
    return MedicalData(
        id = this.id,
        sp02 = this.sp02,
        glucose = this.glucose,
        sbp = this.sbp,
        dbp = this.dbp,
        temperature = this.temperature,
        hrv = this.hrv,
        date = this.date,
        patientID = this.patientID
    )
}