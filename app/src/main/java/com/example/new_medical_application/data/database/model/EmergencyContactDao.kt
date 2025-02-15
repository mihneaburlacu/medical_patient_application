package com.example.new_medical_application.data.database.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EmergencyContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(emergencyContact: EmergencyContactEntity): Long

    @Query("select * from emergency_contact")
    suspend fun getAll(): List<EmergencyContactEntity>

    @Query("select * from emergency_contact where patientId Like :patientId")
    suspend fun getByPatientId(patientId: Long): List<EmergencyContactEntity>

    @Query("select * from emergency_contact where id Like :id")
    suspend fun getContactById(id: Long): EmergencyContactEntity

    @Query("DELETE FROM emergency_contact WHERE id Like :id")
    suspend fun deleteContactById(id: Long)
}