package com.example.new_medical_application.presentation.assistant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.new_medical_application.databinding.FragmentAssistantBinding
import com.example.new_medical_application.presentation.adapters.ChatAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MedicalAssistantFragment : Fragment() {
    private var _binding: FragmentAssistantBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MedicalAssistantViewModel by viewModels()
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAssistantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupListeners()
        observeMessages()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter(emptyList())
        binding.assistantRecyclerview.apply {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(requireContext()).apply {
                stackFromEnd = true
            }
        }
    }

    private fun setupListeners() {
        binding.buttonSend.setOnClickListener {
            val message = binding.editMessage.text.toString().trim()
            if (message.isNotEmpty()) {
                viewModel.sendMessage(message)
                binding.editMessage.setText("")
            }
        }
    }

    private fun observeMessages() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.messages.collectLatest { messages ->
                    chatAdapter.updateMessages(messages)
                    binding.assistantRecyclerview.scrollToPosition(messages.size - 1)
                }
            }
        }
    }
}