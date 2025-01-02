package com.example.new_medical_application.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.new_medical_application.data.database.DateConverter
import java.sql.Timestamp
import java.util.Date
import java.util.UUID

@Entity(tableName = "medical_data")
@TypeConverters(DateConverter::class)
class MedicalDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    var sp02: Int,
    var glucose: Int,
    var sbp: Int,
    var dbp: Int,
    var temperature: Double,
    var hrv: Double,
    var date: Date,
    var patientID: UUID
)