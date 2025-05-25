package com.example.new_medical_application.business.model

data class Message(
    val id: Int,
    val text: String,
    val timestamp: String,
    val date: String,
    val fromMe: Boolean
)