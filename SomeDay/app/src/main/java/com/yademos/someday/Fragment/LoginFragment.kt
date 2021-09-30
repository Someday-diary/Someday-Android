package com.yademos.someday.Fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.yademos.someday.Activity.LoginActivity
import com.yademos.someday.R
import com.yademos.someday.databinding.FragmentLoginBinding
import com.yademos.someday.databinding.FragmentMainBinding
import java.util.regex.Pattern

class LoginFragment : Fragment() {
    val emailValidation =
        "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    var emailCheckResult: Boolean = false
    var passwordCheckResult: Boolean = false
    val FIRST = 1
    val SECOND = 2


    lateinit var binding: FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.signup.setOnClickListener {
            Toast.makeText(activity, "클릭 되었어요.", Toast.LENGTH_SHORT).show()
        }

        binding.email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (checkEmail()) {
                    checkAll(FIRST)
                }
            }
        })

        binding.password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (checkPassword()) {
                    checkAll(SECOND)
                }
            }
        })


        return binding.root
    }

    fun checkEmail(): Boolean {
        var email = binding.email.text.toString().trim() //공백제거
        val pattern = Pattern.matches(emailValidation, email) // 서로 패턴이 맞닝?
        if (pattern) {
            //이메일 형태가 정상일 경우
            binding.emailErrorText.text = ""
            return true
        } else {
            binding.emailErrorText.text = "이메일 형식이 맞지 않습니다."
            return false
        }
    }

    fun checkPassword(): Boolean {
        var password = binding.password.text.toString().trim()
        if (password.length > 4) {
            binding.passwordErrorText.text = ""
            return true
        } else {
            binding.passwordErrorText.text = "비밀번호를 4자리 이상 입력해주세요."
            return false
        }
    }

    fun checkAll(editDetail: Int) {
        if (editDetail == FIRST){
            emailCheckResult = true
            if (passwordCheckResult){

            }
        }else{
            passwordCheckResult = true
            if (emailCheckResult){

            }
        }
    }
}