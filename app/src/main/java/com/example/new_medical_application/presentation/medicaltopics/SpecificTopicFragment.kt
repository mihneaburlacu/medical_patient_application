package com.example.new_medical_application.presentation.medicaltopics

import android.os.Bundle
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.new_medical_application.databinding.FragmentSpecificTopicBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SpecificTopicFragment : Fragment() {
    private var _binding: FragmentSpecificTopicBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SpecificTopicViewModel by viewModels()
    private lateinit var topicId: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        topicId = arguments?.getString("topicId").toString()
        collectContent()
        observeViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpecificTopicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun collectContent() {
        viewModel.fetchMedicalTopicDetail(topicId)
        lifecycleScope.launch {
            viewModel.topicDetail.collectLatest {
                if (it != null) {
                    binding.textTitle.text = it.title
                    binding.textTopic.text = removeHtmlTags(it.content)
                    setVisible()
                }
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.loading.collectLatest {
                binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
    }

    private fun setVisible() {
        binding.textTitle.visibility = View.VISIBLE
        binding.textTopic.visibility = View.VISIBLE
    }

    private fun removeHtmlTags(htmlContent: String): CharSequence {
        val spannedContent: Spanned =
            HtmlCompat.fromHtml(htmlContent, HtmlCompat.FROM_HTML_MODE_LEGACY)
        val regex = "<a[^>]*>(.*?)</a>"
        return spannedContent.toString().replace(regex.toRegex(), "")
    }
}