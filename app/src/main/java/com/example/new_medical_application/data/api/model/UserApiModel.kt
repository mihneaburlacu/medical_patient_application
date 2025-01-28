package com.example.new_medical_application.data.api.model

import com.google.gson.annotations.SerializedName

data class UserApiModel(
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("age")
    val age: Int,
    @SerializedName("dateOfBirth")
    val dateOfBirth: String,
    @SerializedName("encodedId")
    val encodedId: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("memberSince")
    val memberSince: String,
    @SerializedName("height")
    val height: Double,
    @SerializedName("weight")
    val weight: Double
)
