package com.example.new_medical_application.presentation.medicaltopics

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.new_medical_application.business.model.MedicalTopic
import com.example.new_medical_application.domain.repositoryapi.IMedicalTopicsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicalTopicsViewModel @Inject constructor(
    private val repository: IMedicalTopicsRepository
) : ViewModel() {

    private val _medicalTopics = MutableStateFlow<List<MedicalTopic>>(emptyList())
    val medicalTopics: StateFlow<List<MedicalTopic>> = _medicalTopics.asStateFlow()

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        fetchMedicalTopics()
    }

    private fun fetchMedicalTopics() {
        viewModelScope.launch {
            try {
                _medicalTopics.value = repository.getMedicalTopics()
            } catch (e: Exception) {
                Log.e("Mihnea123", "Error fetching topics: ${e.message}")
                _errorMessage.value = "Failed to fetch topics."
            } finally {
                _loading.value = false
            }
        }
    }
}