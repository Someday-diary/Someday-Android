package com.yademos.someday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import java.util.*
import com.prolificinteractive.materialcalendarview.format.TitleFormatter
import com.yademos.someday.databinding.ActivityMainBinding
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()

        val today: CalendarDay = CalendarDay.today()

        binding.calendarView.selectedDate = today
        binding.listDate.text = today.day.toString()

        binding.calendarView.state().edit()
            .setFirstDayOfWeek(Calendar.SUNDAY)
            .setMinimumDate(CalendarDay.from(2020, 12, 31))
            .setMaximumDate(CalendarDay.from(2099, 12, 26))
            .setCalendarDisplayMode(CalendarMode.MONTHS)
            .commit()

        setCalendarViewTitle()
        binding.calendarView.setOnMonthChangedListener { _, _ ->
            setCalendarViewTitle()
        }

        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            binding.listDate.text = date.day.toString()
        }
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_memu)
    }

    private fun setCalendarViewTitle() {
        binding.calendarView.setTitleFormatter(TitleFormatter {
            binding.toolbarTitle.text = it.year.toString() + "년"
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
        when (item.itemId) {
            R.id.menu_search -> {
                //검색 버튼 눌렀을 때
                Toast.makeText(applicationContext, "검색 이벤트 실행", Toast.LENGTH_LONG).show()
                return super.onOptionsItemSelected(item)
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}