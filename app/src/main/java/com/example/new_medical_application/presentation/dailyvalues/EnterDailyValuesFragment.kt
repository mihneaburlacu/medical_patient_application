package com.example.new_medical_application.presentation.dailyvalues

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.new_medical_application.R
import com.example.new_medical_application.business.model.MedicalData
import com.example.new_medical_application.common.TextUtils.showSnackbar
import com.example.new_medical_application.databinding.FragmentDailyValuesBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Date

@AndroidEntryPoint
class EnterDailyValuesFragment : Fragment() {
    private var _binding: FragmentDailyValuesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EnterDailyValuesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListener()
        observeViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDailyValuesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.insertionResult.collect { result ->
                    result?.let {
                        if (it) {
                            showSnackbar(binding.root, getString(R.string.insertion_success))
                        } else {
                            showSnackbar(binding.root, getString(R.string.insertion_failure))
                        }
                    }
                }
            }
        }
    }

    private fun setUpListener() {
        binding.saveButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.patientID.collect {
                        save(it)
                    }
                }
            }
        }
    }

    private fun save(patientID: Long) {
        val spO2 = binding.spO2Input.editText?.text.toString().toIntOrNull()
        val glucose = binding.glucoseInput.editText?.text.toString().toIntOrNull()
        val sbp = binding.sbpInput.editText?.text.toString().toIntOrNull()
        val dbp = binding.dbpInput.editText?.text.toString().toIntOrNull()
        val temperature = binding.temperatureInput.editText?.text.toString().toDoubleOrNull()
        val hrv = binding.hrvInput.editText?.text.toString().toDoubleOrNull()
//        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//        val formattedDate = dateFormat.format(Date())

        if (spO2 == null || glucose == null || sbp == null || dbp == null || temperature == null || hrv == null) {
            showSnackbar(binding.root, getString(R.string.insertion_fail_input))
            return
        }

        val medicalData = MedicalData(
            0, spO2, glucose, sbp, dbp, temperature, hrv, Date(), patientID
        )
        insertMedicalData(medicalData)
    }

    private fun insertMedicalData(medicalData: MedicalData) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.insertMedicalData(medicalData)
            }
        }
    }
}