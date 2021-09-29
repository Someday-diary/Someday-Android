package com.yademos.someday.Fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.yademos.someday.R
import com.yademos.someday.databinding.FragmentDiaryBinding
import androidx.annotation.NonNull
import androidx.annotation.Nullable


class DiaryFragment : Fragment() {

    fun newInstance(index: Int): DiaryFragment {
        val f = DiaryFragment()

        val args = Bundle()
        args.putInt("index", index)
        f.arguments = args
        return f
    }

    fun getShownIndex(): Int {
        return requireArguments().getInt("index", 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDiaryBinding.inflate(inflater, container, false)
        initToolbar(binding)
        return binding.root
    }

    private fun initToolbar(binding: FragmentDiaryBinding) {
        val activity = activity as AppCompatActivity
        activity.apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }
}