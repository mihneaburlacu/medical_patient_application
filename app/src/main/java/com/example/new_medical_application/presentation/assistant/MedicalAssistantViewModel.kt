package com.example.new_medical_application.presentation.assistant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.new_medical_application.business.model.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicalAssistantViewModel @Inject constructor() : ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages

    fun sendMessage(text: String) {
        val newMessage = Message(
            id = _messages.value.size + 1,
            text = text,
            timestamp = "12:00",
            date = "2025-05-25",
            fromMe = true
        )
        _messages.value = _messages.value + newMessage

        viewModelScope.launch {
            val response = Message(
                id = _messages.value.size + 1,
                text = "This is a response to: \"$text\"",
                timestamp = "12:00",
                date = "2025-05-25",
                fromMe = false
            )
            _messages.value = _messages.value + response
        }
    }
}
