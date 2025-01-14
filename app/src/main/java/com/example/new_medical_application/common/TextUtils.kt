package com.example.new_medical_application.common

import android.view.View
import com.google.android.material.snackbar.Snackbar

object TextUtils {
    fun showSnackbar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    fun String.isPasswordValid(): Boolean {
        val passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"
        return passwordRegex.toRegex().matches(this)
    }
}