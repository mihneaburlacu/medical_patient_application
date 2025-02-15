package com.example.new_medical_application.presentation.emergencycontacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.new_medical_application.business.model.EmergencyContact
import com.example.new_medical_application.common.TextUtils.showSnackbar
import com.example.new_medical_application.databinding.DialogAddContactBinding

class AddContactDialog(private val viewModel: EmergencyContactsViewModel) : DialogFragment() {
    private var _binding: DialogAddContactBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            val name = binding.nameInput.editText?.text.toString()
            val phone = binding.phoneInput.editText?.text.toString()
            val email = binding.emailInput.editText?.text.toString()

            if (name.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty()) {
                val newContact = EmergencyContact(0, name, phone, email, 1)
                viewModel.addEmergencyContact(newContact)
                showSnackbar(binding.root, "Contact saved successfully!")
                dismiss()
            } else {
                showSnackbar(binding.root, "All fields are required!")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
