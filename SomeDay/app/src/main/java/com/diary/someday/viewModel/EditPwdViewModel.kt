package com.diary.someday.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diary.someday.application.Application

class EditPwdViewModel : ViewModel() {
    private val _num1 = MutableLiveData<Int>()
    private val _num2 = MutableLiveData<Int>()
    private val _num3 = MutableLiveData<Int>()
    private val _num4 = MutableLiveData<Int>()

    private var _num1State = MutableLiveData<Boolean>()
    private var _num2State = MutableLiveData<Boolean>()
    private var _num3State = MutableLiveData<Boolean>()
    private var _num4State = MutableLiveData<Boolean>()

    val num1State: LiveData<Boolean>
        get() = _num1State
    val num2State: LiveData<Boolean>
        get() = _num2State
    val num3State: LiveData<Boolean>
        get() = _num3State
    val num4State: LiveData<Boolean>
        get() = _num4State

    init {
        _num1.value = -1
        _num2.value = -1
        _num3.value = -1
        _num4.value = -1
    }

    fun addNumber(num: Int) {
        if (_num3.value != -1) {
            _num4.value = num
            _num4State.value = true
        } else if (_num2.value != -1) {
            _num3.value = num
            _num3State.value = true
        } else if (_num1.value != -1) {
            _num2.value = num
            _num2State.value = true
        } else {
            _num1.value = num
            _num1State.value = true
        }
    }

    fun deleteNumber() {
        if (_num3.value != -1) {
            _num3.value = -1
            _num3State.value = false
        } else if (_num2.value != -1) {
            _num2.value = -1
            _num2State.value = false
        } else if (_num1.value != -1) {
            _num1.value = -1
            _num1State.value = false
        }
    }

    fun setPreference() {
        val pwd =
            _num1.value.toString() +
                    _num2.value.toString() +
                    _num3.value.toString() +
                    _num4.value.toString()
        Application.lockNumber.addNumber(pwd)
    }
}