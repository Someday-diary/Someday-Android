package com.yademos.someday.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.yademos.someday.R
import com.yademos.someday.databinding.FragmentAddAlarmBinding

class AddAlarmFragment : Fragment() {
    lateinit var binding: FragmentAddAlarmBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddAlarmBinding.inflate(inflater, container, false)

//        binding.numberPicekrNotification.selectionDividerHeight =
        binding.numberPicekrNotification.selectionDividerHeight = 50
        return binding.root
    }
}