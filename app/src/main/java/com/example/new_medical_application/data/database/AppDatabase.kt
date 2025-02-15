package com.example.new_medical_application.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.new_medical_application.data.database.model.EmergencyContactDao
import com.example.new_medical_application.data.database.model.EmergencyContactEntity
import com.example.new_medical_application.data.database.model.MedicalDataDao
import com.example.new_medical_application.data.database.model.MedicalDataEntity
import com.example.new_medical_application.data.database.model.PatientDao
import com.example.new_medical_application.data.database.model.PatientEntity

@Database(entities = [PatientEntity::class, MedicalDataEntity::class, EmergencyContactEntity::class], version = 5)
abstract class AppDatabase : RoomDatabase() {
    abstract fun patientDao(): PatientDao
    abstract fun medicalDataDao(): MedicalDataDao
    abstract fun emergencyContactDao(): EmergencyContactDao
}