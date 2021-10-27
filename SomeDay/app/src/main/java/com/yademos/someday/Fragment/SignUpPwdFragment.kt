package com.yademos.someday.Fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yademos.someday.Enum.ResponseState
import com.yademos.someday.R
import com.yademos.someday.Retrofit.RetrofitManager
import com.yademos.someday.Viewmodel.SignUpViewModel
import com.yademos.someday.databinding.FragmentSignUpPwdBinding

class SignUpPwdFragment : Fragment() {
    lateinit var binding: FragmentSignUpPwdBinding
    lateinit var signUpViewModel: SignUpViewModel
    val args: SignUpPwdFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpPwdBinding.inflate(inflater, container, false)
        signUpViewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        signUpViewModel.buttonStateSignUp.observe(activity as LifecycleOwner, Observer {
            if (it == true) {
                binding.buttonSignup.setBackgroundResource(R.drawable.ic_login_button_abled)
                binding.buttonSignup.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
                binding.buttonSignup.isEnabled = true
            } else {
                binding.buttonSignup.setBackgroundResource(R.drawable.ic_login_button_enabled)
                binding.buttonSignup.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white_dark
                    )
                )
                binding.buttonSignup.isEnabled = false
            }
        })

        binding.checkBoxLayout.setOnClickListener {
            binding.checkbox.isChecked = !binding.checkbox.isChecked
        }
        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_signUpPwdFragment_to_signUpFragment)
        }
        binding.signinButton.setOnClickListener {
            findNavController().navigate(R.id.action_signUpPwdFragment_to_signInFragment)
        }

        binding.password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(text: Editable?) {
                signUpViewModel.checkPasswordLength(text.toString())
            }

        })
        binding.passwordCheck.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(text: Editable?) {
                signUpViewModel.checkPasswordSame(binding.password.text.toString(), text.toString())
            }

        })

        binding.buttonSignup.setOnClickListener {
            val agree = checkAgree(binding.checkbox.isChecked)
            val email = args.email
            Log.d("TAG", "이메일 $email")
            RetrofitManager.instance.signUp(
                email,
                binding.password.text.toString(),
                agree
            ) { responseState, code ->
                Log.d("TAG", "서버 결과 -> $responseState $code")
                when (responseState) {
                    ResponseState.OKAY -> {
                        findNavController().navigate(R.id.action_signUpPwdFragment_to_signInFragment2)
                    }
                    ResponseState.FAIL -> {
                        Toast.makeText(activity, "$code", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }



        return binding.root
    }

    fun checkAgree(agree: Boolean): String = when (agree) {
        true -> "Y"
        false -> "N"
    }
}