package com.example.new_medical_application.presentation.emergencycontacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.new_medical_application.databinding.FragmentEmergencyContactsBinding
import com.example.new_medical_application.presentation.adapters.ContactListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EmergencyContactsFragment : Fragment() {
    private var _binding: FragmentEmergencyContactsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EmergencyContactsViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var contactAdapter: ContactListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseRecyclerView()
        setupAddButton()
        observeViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmergencyContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initialiseRecyclerView() {
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        contactAdapter = ContactListAdapter(
            emptyList(),
            onTopicClick = { phoneNumber ->

            },
            onButtonClick = { id ->
                viewModel.deleteEmergencyContact(id)
            })
        recyclerView.adapter = contactAdapter
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.emergencyContactsList.collect { contacts ->
                contactAdapter.updateList(contacts)
            }
        }
    }

    private fun setupAddButton() {
        binding.addContactButton.setOnClickListener {
            val addContactDialog = AddContactDialog(viewModel)
            addContactDialog.show(parentFragmentManager, "AddContactDialog")
            refreshEmergencyList()
        }
    }

    private fun refreshEmergencyList() {
        lifecycleScope.launch {
            delay(10)
        }
        observeViewModel()
    }
}