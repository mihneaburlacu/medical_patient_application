package com.example.new_medical_application.presentation.assistant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.new_medical_application.business.model.Message
import com.example.new_medical_application.domain.repositoryapi.IMedicalAssistantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicalAssistantViewModel @Inject constructor(
    private val medicalAssistantRepository: IMedicalAssistantRepository
) : ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun sendMessage(text: String) {
        val now = java.time.LocalDateTime.now()
        val date = now.toLocalDate().toString()
        val time = now.toLocalTime().withSecond(0).withNano(0).toString()

        val userMessage = Message(
            id = _messages.value.size + 1,
            text = text,
            timestamp = time,
            date = date,
            fromMe = true
        )
        _messages.value = _messages.value + userMessage

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = medicalAssistantRepository.askMedical(text)

                val responseMessage = Message(
                    id = _messages.value.size + 1,
                    text = response.response,
                    timestamp = time,
                    date = date,
                    fromMe = false
                )
                _messages.value = _messages.value + responseMessage
            } catch (e: Exception) {
                val errorMessage = Message(
                    id = _messages.value.size + 1,
                    text = "Error: ${e.message}",
                    timestamp = time,
                    date = date,
                    fromMe = false
                )
                _messages.value = _messages.value + errorMessage
            } finally {
                _isLoading.value = false
            }
        }
    }
}