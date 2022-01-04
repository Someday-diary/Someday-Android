package com.diary.someday.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.diary.someday.databinding.FragmentFeedbackBinding

import androidx.core.content.ContextCompat

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.diary.someday.Data.request.Feedback
import com.diary.someday.R
import com.diary.someday.application.Application
import com.diary.someday.viewModel.FeedbackViewModel


class FeedbackFragment : Fragment() {

    private lateinit var binding: FragmentFeedbackBinding
    private val viewModel: FeedbackViewModel by viewModels()
    private var colorId = R.color.green1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedbackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        feedback()
        bindingView()
    }

    override fun onResume() {
        super.onResume()
        changeModeColor(Application.themeSettingColor.getThemeTypeColor())
    }

    private fun changeModeColor(number: Int) {
        when (number) {
            1 -> {
                colorId = R.color.green1
                binding.feedbackTitleEditText.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.selector_feedback_edittext_green
                )
                binding.feedbackContentEditText.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.selector_feedback_edittext_green
                )
                binding.feedbackSubmit.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.ic_login_button_abled_green
                )
            }
            2 -> {
                colorId = R.color.blue1
                binding.feedbackTitleEditText.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.selector_feedback_edittext_blue
                )
                binding.feedbackContentEditText.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.selector_feedback_edittext_blue
                )
                binding.feedbackSubmit.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.ic_login_button_abled_blue
                )
            }
            3 -> {
                colorId = R.color.purple1
                binding.feedbackTitleEditText.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.selector_feedback_edittext_purple
                )
                binding.feedbackContentEditText.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.selector_feedback_edittext_purple
                )
                binding.feedbackSubmit.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.ic_login_button_abled_purple
                )
            }
            4 -> {
                colorId = R.color.yellow1
                binding.feedbackTitleEditText.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.selector_feedback_edittext_yellow
                )
                binding.feedbackContentEditText.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.selector_feedback_edittext_yellow
                )
                binding.feedbackSubmit.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.ic_login_button_abled_yellow
                )
            }
            5 -> {
                colorId = R.color.red1
                binding.feedbackTitleEditText.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.selector_feedback_edittext_red
                )
                binding.feedbackContentEditText.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.selector_feedback_edittext_red
                )
                binding.feedbackSubmit.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.ic_login_button_abled_red
                )
            }
        }
    }

    private fun bindingView() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.feedbackTitleEditText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                binding.feedbackTitle.setTextColor(ContextCompat.getColor(requireContext(), colorId))
            } else {
                binding.feedbackTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }
        }
        binding.feedbackContentEditText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                binding.feedbackContent.setTextColor(ContextCompat.getColor(requireContext(), colorId))
            } else {
                binding.feedbackContent.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }
        }
    }

    private fun feedback() {
        binding.feedbackSubmit.setOnClickListener {
            when {
                binding.feedbackTitleEditText.text.isNullOrEmpty() -> {
                    Toast.makeText(activity, "제목이 비어있어요.", Toast.LENGTH_SHORT)
                }
                binding.feedbackContentEditText.text.isNullOrEmpty() -> {
                    Toast.makeText(requireContext(), "내용이 비어있어요.", Toast.LENGTH_SHORT)
                }
                else -> {
                    viewModel.sendFeedback(Feedback(binding.feedbackTitleEditText.text.toString(), binding.feedbackContentEditText.text.toString()))
                    viewModel.isSuccess.observe(viewLifecycleOwner, {
                        when (it) {
                            "Success" -> {
                                Toast.makeText(requireContext(), "피드백이 접수되었습니다.", Toast.LENGTH_SHORT)
                                findNavController().popBackStack()
                            }
                            "Failure" -> {
                                Toast.makeText(requireContext(), "서버 통신 오류", Toast.LENGTH_SHORT)
                            }
                        }
                    })
                }
            }
        }
    }

}