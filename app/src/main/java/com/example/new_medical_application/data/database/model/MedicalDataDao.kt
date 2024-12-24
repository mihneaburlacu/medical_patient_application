package com.example.new_medical_application.data.database.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MedicalDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMedicalData(medicalData: MedicalDataEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMedicalDataAll(list: List<MedicalDataEntity>): List<Long>

    @Query("select * from medical_data")
    fun getAll(): List<MedicalDataEntity>

    @Query("select * from medical_data where id Like :id")
    fun getMedicalDataDetailsByID(id: Long): MedicalDataEntity

    @Query("select * from medical_data where patientID Like :patientID")
    fun getMedicalDataDetailsByPatientID(patientID: Long): List<MedicalDataEntity>

    @Query("select * from medical_data where patientID Like :patientID AND date Like :date")
    fun getMedicalDataDetailsByPatientIDAndDate(
        patientID: Long,
        date: Long
    ): List<MedicalDataEntity>

    @Query("DELETE FROM medical_data")
    fun deleteAll()
}