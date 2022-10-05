package com.diary.someday.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.diary.someday.R
import com.diary.someday.databinding.FragmentAddAlarmBinding

class AddAlarmFragment : Fragment() {
    lateinit var binding: FragmentAddAlarmBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddAlarmBinding.inflate(inflater, container, false)

//        binding.numberPicekrNotification.selectionDividerHeight =
        return binding.root
    }
}