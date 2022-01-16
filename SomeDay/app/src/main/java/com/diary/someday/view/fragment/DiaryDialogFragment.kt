package com.diary.someday.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.diary.someday.R
import com.diary.someday.di.application.Application
import com.diary.someday.databinding.FragmentDiaryDialogBinding

class DiaryDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentDiaryDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dialogOk.setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        changeModeColor(Application.themeSettingColor.getThemeTypeColor())
    }

    private fun changeModeColor(number: Int) {
        when (number) {
            1 -> {
                binding.dialogTitle.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.green1
                    )
                )
                binding.dialogContent.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.green1
                    )
                )
                binding.view.setBackgroundColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.green1
                    )
                )
            }
            2 -> {
                binding.dialogTitle.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.blue1
                    )
                )
                binding.dialogContent.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.blue1
                    )
                )
                binding.view.setBackgroundColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.blue1
                    )
                )
            }
            3 -> {
                binding.dialogTitle.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.purple1
                    )
                )
                binding.dialogContent.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.purple1
                    )
                )
                binding.view.setBackgroundColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.purple1
                    )
                )
            }
            4 -> {
                binding.dialogTitle.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.yellow1
                    )
                )
                binding.dialogContent.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.yellow1
                    )
                )
                binding.view.setBackgroundColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.yellow1
                    )
                )
            }
            5 -> {
                binding.dialogTitle.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.red1
                    )
                )
                binding.dialogContent.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.red1
                    )
                )
                binding.view.setBackgroundColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.red1
                    )
                )
            }
        }
    }

}