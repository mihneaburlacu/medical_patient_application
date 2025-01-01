package com.example.new_medical_application.presentation.caretaker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.new_medical_application.databinding.FragmentCaretakerProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CaretakerFragment : Fragment() {
    private var _binding: FragmentCaretakerProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CaretakerViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCaretakerProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}