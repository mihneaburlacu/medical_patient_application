package com.example.new_medical_application.presentation.login

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
import com.example.new_medical_application.databinding.FragmentLoginBinding
import com.example.new_medical_application.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

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
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.resetState()
        _binding = null
    }

    private fun setListeners() {
        binding.apply {
            signUpTextButton.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
            loginButton.setOnClickListener {
                val username = usernameInput.editText?.text.toString()
                val password = passwordInput.editText?.text.toString()
                val isRemembered = checkbox.isChecked
                viewModel.login(username, password, isRemembered)
            }
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginState.collect { state ->
                    when (state) {
                        is LoginViewModel.LoginState.Idle -> {
                        }

                        is LoginViewModel.LoginState.Loading -> {
                        }

                        is LoginViewModel.LoginState.Success -> {
                            findNavController().navigate(R.id.action_loginFragment_to_mainMenuFragment)
                        }

                        is LoginViewModel.LoginState.Error -> {
                            showSnackbar(binding.root, state.message)
                        }
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.rememberMeChecked.collect { rememberMeChecked ->
                    if (rememberMeChecked) {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.loginLayout.visibility = View.GONE
                        findNavController().navigate(R.id.action_loginFragment_to_mainMenuFragment)
                    } else {
                        binding.progressBar.visibility = View.GONE
                        binding.loginLayout.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun setInvisibleNavigationDrawer() {
        (requireActivity() as MainActivity).hideNavigationDrawer()
    }
}