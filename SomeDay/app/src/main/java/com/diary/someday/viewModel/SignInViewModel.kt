package com.diary.someday.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diary.someday.model.network.dto.request.user.SignIn
import com.diary.someday.model.network.util.PreferenceUtils
import com.diary.someday.model.repository.SignInRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
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

class SignInViewModel(private val repo: SignInRepository) : ViewModel() {
    private val emailValidation =
        "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

    val code = MutableLiveData<Int>()

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

    fun signIn(signIn: SignIn) {
        repo.signIn(signIn).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.isSuccessful) {
                    code.postValue(it.body()?.code)
                    PreferenceUtils.token = it.body()?.token
                } else {
                    code.postValue(it.body()?.code)
                }
            }, {
                Log.d("Error", it.message.toString())
            })
    }

    fun checkEmail(email: String) {
        val pattern = Pattern.matches(emailValidation, email) // ?????? ????????? ???????
        if (pattern) {
            _emailError.value = "?????????"
            emailType = TYPE.EMAILOK
            _buttonState.value = checkAll()
        } else {
            _emailError.value = "????????? ????????? ?????? ????????????."
            emailType = TYPE.EMAILFAIL
            checkButtonState()
        }
    }

    fun checkPassword(password: String) {
        if (password.length >= 4) {
            _passwordError.value = "????????????"
            pwdType = TYPE.PWDOK
            _buttonState.value = checkAll()
        } else {
            _passwordError.value = "??????????????? 4?????? ?????? ??????????????????."
            pwdType = TYPE.PWDFAIL
            checkButtonState()
        }
    }

    private fun checkAll(): Boolean = pwdType == TYPE.PWDOK && emailType == TYPE.EMAILOK

    private fun checkButtonState() {
        if (_buttonState.value == true) _buttonState.value = false
    }


}