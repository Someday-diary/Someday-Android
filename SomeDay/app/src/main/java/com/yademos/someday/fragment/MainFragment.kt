package com.yademos.someday.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.format.TitleFormatter
import com.yademos.someday.R
import com.yademos.someday.databinding.FragmentMainBinding
import java.text.SimpleDateFormat
import java.util.*
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        setHasOptionsMenu(true);
        initToolbar(binding) // Toolbar 설정
        initCalendarView(binding) // CalendarView 설정
        setCalendarViewTitle(binding) // 이번 달

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState != BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheet.background = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.background_bottom_sheet_sliding,
                        null
                    )
                } else {
                    bottomSheet.background = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.background_bottom_sheet,
                        null
                    )
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

        })

        binding.calendarView.setOnMonthChangedListener { _, _ -> // 그 다음달부터 달력 넘겼을때
            setCalendarViewTitle(binding)
        }

        binding.calendarView.setOnDateChangedListener { _, date, _ ->
            binding.listDate.text = date.day.toString()
        }

        binding.listEdit.setOnClickListener {
            val date = binding.calendarView.selectedDate.date
            val longDate = convertedDateToLong(date)
            val year = binding.calendarView.selectedDate.year.toString()
            val month = (binding.calendarView.selectedDate.month + 1).toString()
            val day = binding.calendarView.selectedDate.day.toString()
//            Navigation.findNavController(binding.root).navigate(R.id.action_mainFragment_to_diaryFragment)
//            findNavController().navigate(MainFragmentDirections.actionMainFragmentToDiaryFragment(year, month, day, date))
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToDiaryFragment(year, month, day, longDate))
        }
//
        return binding.root
    }

    private fun initToolbar(binding: FragmentMainBinding) {
        val activity = activity as AppCompatActivity

        activity.apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.apply {
                setDisplayShowTitleEnabled(false)
                setDisplayHomeAsUpEnabled(true)
                setHomeAsUpIndicator(R.drawable.ic_memu)
            }
        }
    }

    private fun initCalendarView(binding: FragmentMainBinding) {
        val today: CalendarDay = CalendarDay.today()

        binding.calendarView.selectedDate = today
        binding.listDate.text = today.day.toString()

        binding.calendarView.state().edit()
            .setFirstDayOfWeek(Calendar.SUNDAY)
            .setMinimumDate(CalendarDay.from(2020, 12, 31))
            .setMaximumDate(CalendarDay.from(2099, 12, 26))
            .setCalendarDisplayMode(CalendarMode.MONTHS)
            .commit()
    }

    private fun setCalendarViewTitle(binding: FragmentMainBinding) {
        binding.calendarView.setTitleFormatter(TitleFormatter {
            binding.toolbarTitle.text = it.year.toString() + "년"
            val calendarViewFormat = SimpleDateFormat("M월")
            calendarViewFormat.format(it.date.time)
        })
    }

    private fun convertedDateToLong(date: Date): Long {
        return date.time
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    //액션버튼 클릭 했을 때
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_search -> {
                //검색 버튼 눌렀을 때
                return super.onOptionsItemSelected(item)
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}