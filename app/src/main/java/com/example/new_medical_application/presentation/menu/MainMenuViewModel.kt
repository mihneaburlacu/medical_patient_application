package com.example.new_medical_application.presentation.menu

import androidx.lifecycle.ViewModel
import com.example.new_medical_application.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor() : ViewModel() {
    fun handleCardClick(cardType: CardType): Int {
        return when (cardType) {
            CardType.DAILY_VALUES -> R.id.enterDailyValuesFragment
            CardType.PHYSIOLOGICAL_DATA -> R.id.physiologicalDataFragment
            CardType.EMERGENCY_CONTACTS -> R.id.emergencyContactsFragment
            CardType.CARETAKER -> R.id.caretakerFragment
            CardType.MEDICAL_TOPICS -> R.id.medicalTopicsFragment
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