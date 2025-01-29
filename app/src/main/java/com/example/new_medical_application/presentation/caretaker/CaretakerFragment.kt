package com.example.new_medical_application.presentation.caretaker

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.new_medical_application.common.AppConstants.DEFAULT_EMAIL_MSG
import com.example.new_medical_application.common.AppConstants.DEFAULT_SMS_MSG
import com.example.new_medical_application.common.AppConstants.EMAIL_ADDRESS
import com.example.new_medical_application.common.AppConstants.EMAIL_SUBJECT
import com.example.new_medical_application.common.AppConstants.TELEPHONE_NUMBER
import com.example.new_medical_application.common.TextUtils.showSnackbar
import com.example.new_medical_application.databinding.FragmentCaretakerProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CaretakerFragment : Fragment() {
    private var _binding: FragmentCaretakerProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CaretakerViewModel by viewModels()

    private val requestCallPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                callCaretaker()
            } else {
                showSnackbar(binding.root, "Permission denied to make a phone call")
            }
        }

    private val requestSendSmsPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                sendSMS()
            } else {
                showSnackbar(binding.root, "Permission denied to sms")
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        observeButtonClicks()
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

    private fun setUpListeners() {
        binding.callButton.setOnClickListener {
            viewModel.onButtonClicked(CaretakerViewModel.ButtonEvent.INTERMEDIARY)
            viewModel.onButtonClicked(CaretakerViewModel.ButtonEvent.CALL)
        }
        binding.smsButton.setOnClickListener {
            viewModel.onButtonClicked(CaretakerViewModel.ButtonEvent.INTERMEDIARY)
            viewModel.onButtonClicked(CaretakerViewModel.ButtonEvent.SMS)
        }
        binding.emailButton.setOnClickListener {
            viewModel.onButtonClicked(CaretakerViewModel.ButtonEvent.INTERMEDIARY)
            viewModel.onButtonClicked(CaretakerViewModel.ButtonEvent.EMAIL)
        }
    }

    private fun observeButtonClicks() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.buttonClickEvent.collectLatest { event ->
                    event?.let { handleButtonAction(it) }
                }
            }
        }
    }

    private fun handleButtonAction(event: CaretakerViewModel.ButtonEvent) {
        when (event) {
            CaretakerViewModel.ButtonEvent.CALL -> requestCallPermission()
            CaretakerViewModel.ButtonEvent.SMS -> requestSendSmsPermission()
            CaretakerViewModel.ButtonEvent.EMAIL -> sendEmail()
            CaretakerViewModel.ButtonEvent.INTERMEDIARY -> handleIntermediaryState()
        }
    }

    private fun handleIntermediaryState() {
        //Do nothing, because we need to have an intermediary state
        //if someone wants to click twice on the same button
    }

    private fun requestCallPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestCallPermission.launch(Manifest.permission.CALL_PHONE)
        } else {
            callCaretaker()
        }
    }

    private fun callCaretaker() {
        viewModel.onButtonClicked(CaretakerViewModel.ButtonEvent.INTERMEDIARY)
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$TELEPHONE_NUMBER")
        startActivity(callIntent)
    }

    private fun requestSendSmsPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestSendSmsPermission.launch(Manifest.permission.SEND_SMS)
        } else {
            sendSMS()
        }
    }

    @SuppressLint("IntentReset")
    private fun sendSMS() {
        viewModel.onButtonClicked(CaretakerViewModel.ButtonEvent.INTERMEDIARY)
        val sendIntent = Intent(Intent.ACTION_VIEW)
        sendIntent.data = Uri.parse("smsto:")
        sendIntent.type = "vnd.android-dir/mms-sms"
        sendIntent.putExtra("address", TELEPHONE_NUMBER)
        sendIntent.putExtra("sms_body", DEFAULT_SMS_MSG)
        startActivity(sendIntent)
    }

    private fun sendEmail() {
        viewModel.onButtonClicked(CaretakerViewModel.ButtonEvent.INTERMEDIARY)
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(EMAIL_ADDRESS))
            putExtra(Intent.EXTRA_SUBJECT, EMAIL_SUBJECT)
            putExtra(Intent.EXTRA_TEXT, DEFAULT_EMAIL_MSG)
        }
        if (intent.resolveActivity(requireContext().packageManager) != null) {
            startActivity(intent)
        } else {
            showSnackbar(binding.root, "No email app found")
        }
    }
}