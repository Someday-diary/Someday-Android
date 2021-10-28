package com.yademos.someday.Viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.regex.Pattern

enum class TYPE {
    EMAILOK,
    EMAILFAIL,
    PWDOK,
    PWDFAIL,
    SENDOK,
    SENDFAIL,
    PWD1OK,
    PWD1FAIL,
    PWD2OK,
    PWD2FAIL
}

class SignInViewModel : ViewModel() {
    private val emailValidation =
        "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

    private val _buttonState = MutableLiveData<Boolean>()
    val buttonState: LiveData<Boolean>
        get() = _buttonState

    private val _emailError = MutableLiveData<String>()
    private val _passwordError = MutableLiveData<String>()

    private var emailType = TYPE.EMAILFAIL
    private var pwdType = TYPE.PWDFAIL

    val emailError: LiveData<String>
        get() = _emailError
    val passwordError: LiveData<String>
        get() = _passwordError

    init {
        _emailError.value = ""
        _passwordError.value = ""
        _buttonState.value = false
    }

    fun clickToast(type: String, context: Context) {
        Toast.makeText(context, "${type}이 클릭되었습니다.", Toast.LENGTH_SHORT).show()
    }

    fun checkEmail(email: String) {
        val pattern = Pattern.matches(emailValidation, email) // 서로 패턴이 맞닝?
        if (pattern) {
            _emailError.value = "이메일"
            emailType = TYPE.EMAILOK
            _buttonState.value = checkAll()
        } else {
            _emailError.value = "이메일 형식이 맞지 않습니다."
            emailType = TYPE.EMAILFAIL
            checkButtonState()
        }
    }

    fun checkPassword(password: String) {
        if (password.length >= 4) {
            _passwordError.value = "비밀번호"
            pwdType = TYPE.PWDOK
            _buttonState.value = checkAll()
        } else {
            _passwordError.value = "비밀번호를 4자리 이상 입력해주세요."
            pwdType = TYPE.PWDFAIL
            checkButtonState()
        }
    }

    private fun checkAll(): Boolean = pwdType == TYPE.PWDOK && emailType == TYPE.EMAILOK

    private fun checkButtonState() {
        if (_buttonState.value == true) _buttonState.value = false
    }


}