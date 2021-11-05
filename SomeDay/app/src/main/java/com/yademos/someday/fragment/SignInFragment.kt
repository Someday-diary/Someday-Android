package com.yademos.someday.Fragment


import android.content.Context
import android.content.Intent
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
import com.yademos.someday.Enum.ResponseState
import com.yademos.someday.R
import com.yademos.someday.Retrofit.RetrofitManager
import com.yademos.someday.Viewmodel.SignInViewModel
import com.yademos.someday.activity.MainActivity
import com.yademos.someday.application.Application
import com.yademos.someday.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {

    lateinit var signInViewModel: SignInViewModel
    lateinit var binding: FragmentSignInBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        signInViewModel = ViewModelProvider(this).get(SignInViewModel::class.java)




        binding.signupButton.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
            signInViewModel.clickToast("회원가입 버튼", activity as Context)
        }

        signInViewModel.emailError.observe(activity as LifecycleOwner, Observer {
            binding.emailErrorText.text = it.toString()
        })

        signInViewModel.passwordError.observe(activity as LifecycleOwner, Observer {
            binding.passwordErrorText.text = it.toString()
        })

        signInViewModel.buttonState.observe(activity as LifecycleOwner, Observer {
            if (it == true) {
                binding.buttonSignin.setBackgroundResource(R.drawable.ic_login_button_abled)
                binding.buttonSignin.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
                binding.buttonSignin.isEnabled = true
            } else {
                binding.buttonSignin.setBackgroundResource(R.drawable.ic_login_button_enabled)
                binding.buttonSignin.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white_dark
                    )
                )
                binding.buttonSignin.isEnabled = false
            }
        })

        binding.buttonSignin.setOnClickListener {
            Log.d("TAG", "버튼 클릭")
            RetrofitManager.instance.signIn(
                binding.email.text.toString(),
                binding.password.text.toString()
            ) { responseState, msg ->
                Log.d("TAG", "서버 결과 -> $responseState $msg")
                when (responseState) {
                    ResponseState.OKAY -> {
                        signInViewModel.clickToast("로그인 버튼", activity as Context)
//                        findNavController().navigate(R.id.action_signInFragment_to_mainFragment2)

                        val intent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intent)
                    }
                    ResponseState.FAIL -> {
                        Toast.makeText(activity, "$msg", Toast.LENGTH_SHORT).show()
                    }
                }
            }
//            startActivity(Intent(activity, MainActivity::class.java))
        }

        binding.email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val email = binding.email.text.toString()
                signInViewModel.checkEmail(email)
            }
        })

        binding.password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val password = binding.password.text.toString()
                signInViewModel.checkPassword(password)
            }
        })


        return binding.root
    }


    fun changedButton() {
        binding.buttonSignin.setBackgroundResource(R.drawable.ic_login_button_abled)
        binding.buttonSignin.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.buttonSignin.isEnabled = true
    }
}