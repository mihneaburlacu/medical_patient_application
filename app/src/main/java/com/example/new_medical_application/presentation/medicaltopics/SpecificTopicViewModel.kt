package com.example.new_medical_application.presentation.medicaltopics

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.new_medical_application.business.model.MedicalTopicDetail
import com.example.new_medical_application.domain.repositoryapi.IMedicalTopicDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpecificTopicViewModel @Inject constructor(
    private val repository: IMedicalTopicDetailRepository
) : ViewModel() {
    private val _topicDetail = MutableStateFlow<MedicalTopicDetail?>(null)
    val topicDetail: StateFlow<MedicalTopicDetail?> = _topicDetail

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    fun fetchMedicalTopicDetail(topicId: String) {
        viewModelScope.launch {
            try {
                _topicDetail.value = repository.getMedicalTopic(topicId)
            } catch (e: Exception) {
                Log.e("Mihnea123", "Error fetching the topic: ${e.message}")
            } finally {
                _loading.value = false
            }
        }
    }
}