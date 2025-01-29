package com.example.new_medical_application.presentation.caretaker

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CaretakerViewModel @Inject constructor() : ViewModel() {
    private val _buttonClickEvent = MutableStateFlow<ButtonEvent?>(null)
    val buttonClickEvent = _buttonClickEvent.asStateFlow()

    fun onButtonClicked(event: ButtonEvent) {
        _buttonClickEvent.value = event
    }

    enum class ButtonEvent {
        CALL, SMS, EMAIL, INTERMEDIARY
    }
}