package com.example.new_medical_application.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.new_medical_application.business.model.Patient
import com.example.new_medical_application.common.AppConstants.PREFS_NAME
import com.example.new_medical_application.common.AppConstants.PREFS_PATIENT_KEY
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesHelper @Inject constructor(
    @ApplicationContext context: Context
) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val gson = Gson()

    fun savePatient(patient: Patient) {
        val patientJson = gson.toJson(patient)
        sharedPreferences.edit().putString(PREFS_PATIENT_KEY, patientJson).apply()
//        sharedPreferences.edit().putString(PREFS_PATIENT_KEY, patientJson).apply()
    }

    fun getPatient(): Patient? {
        val patientJson = sharedPreferences.getString(PREFS_PATIENT_KEY, null)
        return patientJson?.let { gson.fromJson(it, Patient::class.java) }
    }

    fun clearPatient() {
        sharedPreferences.edit().remove(PREFS_PATIENT_KEY).apply()
    }
}