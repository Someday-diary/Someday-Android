package com.yademos.someday.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yademos.someday.R
import com.yademos.someday.databinding.FragmentLoginBinding
import com.yademos.someday.databinding.FragmentMainBinding

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLoginBinding.inflate(inflater,container,false)


        return binding.root
    }
}