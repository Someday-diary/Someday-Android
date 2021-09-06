package com.yademos.someday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import java.util.*
import com.prolificinteractive.materialcalendarview.format.TitleFormatter
import java.text.SimpleDateFormat


class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var calendarView: MaterialCalendarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val now: Long = System.currentTimeMillis()
        val date = Date(now)

        toolbar = findViewById(R.id.toolbar)
        calendarView = findViewById(R.id.calendarView)

        initToolbar()

        calendarView.state().edit()
            .setFirstDayOfWeek(Calendar.SUNDAY)
            .setMinimumDate(CalendarDay.from(2020, 12, 31))
            .setMaximumDate(CalendarDay.from(2099, 12, 26))
            .setCalendarDisplayMode(CalendarMode.MONTHS)
            .commit()

        calendarView.setSelectedDate(date)

        setCalendarViewTitle()
        calendarView.setOnMonthChangedListener { _, _ ->
            setCalendarViewTitle()
        }
    }


    private fun initToolbar() {
        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_memu)
    }

    private fun setCalendarViewTitle() {
        calendarView.setTitleFormatter(TitleFormatter {
            supportActionBar?.title = it.year.toString()
            val calendar_view_format = SimpleDateFormat("M월")
            calendar_view_format.format(it.date.time)
        })
    }
}