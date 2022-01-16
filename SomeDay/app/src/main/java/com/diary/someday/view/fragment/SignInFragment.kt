package com.diary.someday.Fragment


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
import com.diary.someday.model.network.util.Enum.ResponseState
import com.diary.someday.R
import com.diary.someday.model.network.RetrofitManager
import com.diary.someday.Viewmodel.SignInViewModel
import com.diary.someday.view.activity.MainActivity
import com.diary.someday.di.application.Application
import com.diary.someday.databinding.FragmentSignInBinding
import com.diary.someday.model.network.util.PreferenceUtils

class SignInFragment : Fragment() {

    lateinit var signInViewModel: SignInViewModel
    lateinit var binding: FragmentSignInBinding

    override fun onStart() {
        super.onStart()
        changeModeColor(Application.themeSettingColor.getThemeTypeColor())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        signInViewModel = ViewModelProvider(this).get(SignInViewModel::class.java)


        binding.signupButton.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
//            signInViewModel.clickToast("회원가입 버튼", activity as Context)
        }

        signInViewModel.emailError.observe(activity as LifecycleOwner, Observer {
            binding.emailErrorText.text = it.toString()
        })

        signInViewModel.passwordError.observe(activity as LifecycleOwner, Observer {
            binding.passwordErrorText.text = it.toString()
        })

        signInViewModel.buttonState.observe(activity as LifecycleOwner, Observer {
            changedButton(it)
        })

        binding.buttonSignin.setOnClickListener {
            Log.d("TAG", "버튼 클릭")
            RetrofitManager.instance.signIn(
                binding.email.text.toString(),
                binding.password.text.toString()
            ) { responseState, msg, token ->
                Log.d("TAG", "서버 결과 -> $responseState $msg")
                when (responseState) {
                    ResponseState.OKAY -> {
                        PreferenceUtils.token = token
//                        signInViewModel.clickToast("로그인 버튼", activity as Context)
//                        findNavController().navigate(R.id.action_signInFragment_to_mainFragment2)
                        Application.signInCheck.signIn()
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

    private fun changeModeColor(number: Int) {
        Log.d("TAG", "changeModeColor: number $number")

        when (number) {
            1 -> {
                binding.icon.setImageDrawable(
                    ContextCompat.getDrawable(
                        activity as Context,
                        R.drawable.ic_icon_green
                    )
                )
                binding.gotoSignUp.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.green3
                    )
                )
            }
            2 -> {
                binding.icon.setImageDrawable(
                    ContextCompat.getDrawable(
                        activity as Context,
                        R.drawable.ic_icon_blue
                    )
                )
                binding.gotoSignUp.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.blue3
                    )
                )
            }
            3 -> {
                binding.icon.setImageDrawable(
                    ContextCompat.getDrawable(
                        activity as Context,
                        R.drawable.ic_icon_purple
                    )
                )
                binding.gotoSignUp.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.purple3
                    )
                )
            }
            4 -> {
                binding.icon.setImageDrawable(
                    ContextCompat.getDrawable(
                        activity as Context,
                        R.drawable.ic_icon_yellow
                    )
                )
                binding.gotoSignUp.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.yellow3
                    )
                )
            }
            5 -> {
                binding.icon.setImageDrawable(
                    ContextCompat.getDrawable(
                        activity as Context,
                        R.drawable.ic_icon_red
                    )
                )
                binding.gotoSignUp.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.red3
                    )
                )
            }
        }
    }


    fun changedButton(check: Boolean) {
        if (check) {
            when (Application.themeSettingColor.getThemeTypeColor()) {
                1 -> {
                    binding.buttonSignin.setBackgroundResource(R.drawable.ic_login_button_abled_green)
                }
                2 -> {
                    binding.buttonSignin.setBackgroundResource(R.drawable.ic_login_button_abled_blue)
                }
                3 -> {
                    binding.buttonSignin.setBackgroundResource(R.drawable.ic_login_button_abled_purple)
                }
                4 -> {
                    binding.buttonSignin.setBackgroundResource(R.drawable.ic_login_button_abled_yellow)
                }
                5 -> {
                    binding.buttonSignin.setBackgroundResource(R.drawable.ic_login_button_abled_red)
                }
            }
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
    }
}