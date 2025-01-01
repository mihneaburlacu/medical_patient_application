package com.example.new_medical_application.presentation.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.new_medical_application.R
import com.example.new_medical_application.databinding.FragmentMainMenuBinding
import com.example.new_medical_application.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainMenuFragment : Fragment() {
    private var _binding: FragmentMainMenuBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainMenuViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNavigationDrawer()
        setupCardClickListeners()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showNavigationDrawer() {
        (requireActivity() as MainActivity).showNavigationDrawer(R.id.nav_home)
    }

    private fun checkItemFromNavigationDrawer(navId: Int) {
        (requireActivity() as MainActivity).checkItemAfterNavigation(navId)
    }

    private fun navigateTo(destinationId: Int) {
        findNavController().navigate(destinationId)
    }

    private fun setupCardClickListeners() {
        binding.dailyCard.setOnClickListener {
            navigateTo(viewModel.handleCardClick(MainMenuViewModel.CardType.DAILY_VALUES))
            checkItemFromNavigationDrawer(R.id.nav_values)
        }
        binding.physiologicalCard.setOnClickListener {
            navigateTo(viewModel.handleCardClick(MainMenuViewModel.CardType.PHYSIOLOGICAL_DATA))
            checkItemFromNavigationDrawer(R.id.nav_physiological_data)
        }
        binding.emergencyContactsCard.setOnClickListener {
            navigateTo(viewModel.handleCardClick(MainMenuViewModel.CardType.EMERGENCY_CONTACTS))
            checkItemFromNavigationDrawer(R.id.nav_contacts)
        }
        binding.caretakerCard.setOnClickListener {
            navigateTo(viewModel.handleCardClick(MainMenuViewModel.CardType.CARETAKER))
            checkItemFromNavigationDrawer(R.id.nav_caregiver)
        }
        binding.topicsCard.setOnClickListener {
            navigateTo(viewModel.handleCardClick(MainMenuViewModel.CardType.MEDICAL_TOPICS))
            checkItemFromNavigationDrawer(R.id.nav_medical_topics)
        }
    }
}