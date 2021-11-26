package com.diary.someday.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.diary.someday.R
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

}