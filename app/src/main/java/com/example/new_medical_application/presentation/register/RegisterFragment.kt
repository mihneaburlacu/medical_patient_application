package com.example.new_medical_application.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.new_medical_application.R
import com.example.new_medical_application.common.TextUtils.showSnackbar
import com.example.new_medical_application.databinding.FragmentRegisterBinding
import com.example.new_medical_application.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        observeViewModel()
        setInvisibleNavigationDrawer()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.resetState()
        _binding = null
    }

    private fun setListeners() {
        binding.apply {
            registerButton.setOnClickListener {
                val username = binding.usernameInput.editText?.text.toString()
                val password = binding.passwordInput.editText?.text.toString()
                val email = binding.emailInput.editText?.text.toString()
                val name = binding.nameInput.editText?.text.toString()
                val phoneNumber = binding.phoneInput.editText?.text.toString()
                val fitbitId = binding.fitbitInput.editText?.text.toString()
                viewModel.register(username, password, email, name, phoneNumber, fitbitId)
            }
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registrationState.collect { state ->
                    when (state) {
                        is RegisterViewModel.RegistrationState.Idle -> {
                        }

                        is RegisterViewModel.RegistrationState.Loading -> {
                        }

                        is RegisterViewModel.RegistrationState.Success -> {
                            findNavController().popBackStack()
                        }

                        is RegisterViewModel.RegistrationState.Error -> {
                            showSnackbar(binding.root, state.message)
                        }
                    }
                }
            }
        }
    }

    private fun setInvisibleNavigationDrawer() {
        (requireActivity() as MainActivity).hideNavigationDrawer()
    }
}