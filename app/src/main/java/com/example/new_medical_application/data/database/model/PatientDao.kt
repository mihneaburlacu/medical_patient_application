package com.example.new_medical_application.data.database.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PatientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPatient(patient: PatientEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPatientAll(patients: List<PatientEntity>): List<Long>

    @Query("SELECT * FROM patients WHERE username LIKE :username AND password LIKE :password")
    fun readLoginData(username: String, password: String): PatientEntity

    @Query("select * from patients where id Like :id")
    fun getPatientDataDetailsById(id: Long): PatientEntity

    @Query("select * from patients where username Like :username")
    fun getPatientDataDetailsByUsername(username: String): List<PatientEntity>

    @Query("select * from patients")
    fun getAllPatients(): List<PatientEntity>

    @Query("DELETE FROM patients")
    fun deleteAll()
}