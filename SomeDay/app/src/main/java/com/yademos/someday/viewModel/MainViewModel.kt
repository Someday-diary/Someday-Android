package com.yademos.someday.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var year = MutableLiveData<String>()
    var month = MutableLiveData<String>()
    var day = MutableLiveData<String>()
    var date = MutableLiveData<Long>()

    fun setYear(year: String) {
        this.year.value = year
    }

    fun setMonth(month: String) {
        this.month.value = month
    }

    fun setDay(day: String) {
        this.day.value = day
    }

    fun setDate(date: Long) {
        this.date.value = date
    }

}