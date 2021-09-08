package com.yademos.someday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import java.util.*
import com.prolificinteractive.materialcalendarview.format.TitleFormatter
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var calendarView: MaterialCalendarView
    private lateinit var toolbarTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val now: Long = System.currentTimeMillis()
        val date = Date(now)

        toolbar = findViewById(R.id.toolbar)
        calendarView = findViewById(R.id.calendarView)
        toolbarTitle = findViewById(R.id.toolbarTitle)

        initToolbar()

        calendarView.setSelectedDate(date)
        calendarView.state().edit()
            .setFirstDayOfWeek(Calendar.SUNDAY)
            .setMinimumDate(CalendarDay.from(2020, 12, 31))
            .setMaximumDate(CalendarDay.from(2099, 12, 26))
            .setCalendarDisplayMode(CalendarMode.MONTHS)
            .commit()

        setCalendarViewTitle()
        calendarView.setOnMonthChangedListener { _, _ ->
            setCalendarViewTitle()
        }

    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_memu)
    }

    private fun setCalendarViewTitle() {
        calendarView.setTitleFormatter(TitleFormatter {
            toolbarTitle.text = it.year.toString()
            val calendarViewFormat = SimpleDateFormat("M월")
            calendarViewFormat.format(it.date.time)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    //액션버튼 클릭 했을 때
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_search -> {
                //검색 버튼 눌렀을 때
                Toast.makeText(applicationContext, "검색 이벤트 실행", Toast.LENGTH_LONG).show()
                return super.onOptionsItemSelected(item)
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}