package com.example.new_medical_application.presentation.menu

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.new_medical_application.R
import com.example.new_medical_application.business.model.Patient
import com.example.new_medical_application.domain.usecases.IPatientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor(
    private val patientUseCase: IPatientUseCase
) : ViewModel() {
    private val _patient = MutableStateFlow<Patient?>(null)
    val patient: StateFlow<Patient?> = _patient

    init {
        getSavedPatient()
    }

    fun handleCardClick(cardType: CardType): Int {
        return when (cardType) {
            CardType.DAILY_VALUES -> R.id.enterDailyValuesFragment
            CardType.PHYSIOLOGICAL_DATA -> R.id.physiologicalDataFragment
            CardType.EMERGENCY_CONTACTS -> R.id.emergencyContactsFragment
            CardType.CARETAKER -> R.id.caretakerFragment
            CardType.MEDICAL_TOPICS -> R.id.medicalTopicsFragment
        }
    }

    private fun getSavedPatient() {
        viewModelScope.launch(Dispatchers.IO) {
            patientUseCase.getSavedPatientSharedPreference().collect {
                _patient.value = it
            }
        }
    }

    enum class CardType {
        DAILY_VALUES,
        PHYSIOLOGICAL_DATA,
        EMERGENCY_CONTACTS,
        CARETAKER,
        MEDICAL_TOPICS
    }
}