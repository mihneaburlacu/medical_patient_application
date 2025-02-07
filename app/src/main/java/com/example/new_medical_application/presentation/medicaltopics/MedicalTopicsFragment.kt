package com.example.new_medical_application.presentation.medicaltopics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.new_medical_application.business.model.MedicalTopic
import com.example.new_medical_application.databinding.FragmentMedicalTopicsBinding
import com.example.new_medical_application.presentation.adapters.TopicListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MedicalTopicsFragment : Fragment() {
    private var _binding: FragmentMedicalTopicsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MedicalTopicsViewModel by viewModels()

    private val topics = listOf(
        MedicalTopic("100", "Increase spo2"),
        MedicalTopic("300", "Decrease the chance of falling")
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseRecyclerView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMedicalTopicsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initialiseRecyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = TopicListAdapter(topics)
        binding.progressBar.visibility = View.INVISIBLE
    }
}