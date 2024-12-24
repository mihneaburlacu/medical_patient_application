package com.example.new_medical_application.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.new_medical_application.data.database.DateConverter
import java.util.UUID

@Entity(tableName = "patients")
class PatientEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    var username: String = "",
    var password: String = "",
    var email: String = "",
    var name: String = "",
    var phoneNumber: String = "0727455324",
    var fitbitID: String = ""
)