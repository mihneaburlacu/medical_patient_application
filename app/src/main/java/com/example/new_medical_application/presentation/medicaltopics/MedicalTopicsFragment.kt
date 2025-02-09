package com.example.new_medical_application.presentation.medicaltopics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.new_medical_application.R
import com.example.new_medical_application.common.TextUtils.showSnackbar
import com.example.new_medical_application.databinding.FragmentMedicalTopicsBinding
import com.example.new_medical_application.presentation.adapters.TopicListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MedicalTopicsFragment : Fragment() {
    private var _binding: FragmentMedicalTopicsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MedicalTopicsViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseRecyclerView()
        collectMedicalTopics()
        observeViewModel()
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
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun collectMedicalTopics() {
        lifecycleScope.launch {
            viewModel.medicalTopics.collect { topics ->
                recyclerView.adapter = TopicListAdapter(topics) {
                    navigateToTopicDetail(it)
                }
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.loading.collectLatest { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
        lifecycleScope.launch {
            viewModel.errorMessage.collectLatest { error ->
                if (error != null) {
                    showSnackbar(binding.root, error)
                }
            }
        }
    }

    private fun navigateToTopicDetail(topicId: String) {
        if (isAdded) {
            activity?.runOnUiThread {
                val bundle = Bundle().apply {
                    putString("topicId", topicId)
                }
                findNavController().navigate(
                    R.id.action_medicalTopicsFragment_to_specificTopicFragment,
                    bundle
                )
            }

        }
    }
}