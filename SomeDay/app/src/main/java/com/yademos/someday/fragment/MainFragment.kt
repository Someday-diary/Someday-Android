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
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yademos.someday.databinding.DrawerLayoutBinding
import com.yademos.someday.decoration.CalendarDecorator
import com.yademos.someday.viewModel.DiaryViewModel

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var drawerBinding: DrawerLayoutBinding
    private val viewModel: DiaryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        drawerBinding = DrawerLayoutBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true);
        initToolbar() // Toolbar 설정
        initCalendarView() // CalendarView 설정
        setCalendarViewTitle() // 이번 달
        bindingBottomSheetBehavior() // BottomSheet 설정

        return binding.root
    }

    private fun initToolbar() {
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

    private fun initCalendarView() {
        val today: CalendarDay = CalendarDay.today()
        binding.listDate.text = today.day.toString()

        binding.calendarView.apply {
            state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2020, 12, 31))
                .setMaximumDate(CalendarDay.from(2099, 12, 26))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit()
            selectedDate = today
            setOnMonthChangedListener { _, date -> // 그 다음달부터 달력 넘겼을때
                setCalendarViewTitle()
                val existList = existDiary(date.year, date.month)
                for (existDay in existList) {
                    binding.calendarView.addDecorators(
                        CalendarDecorator(
                            requireActivity(),
                            existDay
                        )
                    )
                }
            }
            setOnDateChangedListener { _, date, _ ->
                existDayDiary()
                binding.listDate.text = date.day.toString()
            }
        }
    }

    private fun setCalendarViewTitle() {
        binding.calendarView.setTitleFormatter(TitleFormatter {
            binding.toolbarTitle.text = it.year.toString() + "년"
            val calendarViewFormat = SimpleDateFormat("M월")
            calendarViewFormat.format(it.date.time)
        })
    }

    private fun convertedDateToLong(date: Date): Long {
        return date.time
    }

    private fun bindingBottomSheetBehavior() {
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
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

        binding.listEdit.setOnClickListener {
            val date = binding.calendarView.selectedDate.date
            val longDate = convertedDateToLong(date)
            val year = binding.calendarView.selectedDate.year.toString()
            val month = (binding.calendarView.selectedDate.month + 1).toString()
            val day = binding.calendarView.selectedDate.day.toString()
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToDiaryFragment(
                    year,
                    month,
                    day,
                    longDate
                )
            )
        }
    }

    private fun existDiary(year: Int, month: Int): List<CalendarDay> {
        val existContentList: MutableList<CalendarDay> = mutableListOf()
        viewModel.callGetDiary(emptyList())
        viewModel.diaryListLiveData.observe(viewLifecycleOwner, {
            for (i in 0..it?.diaries!!.size) {
                if (it.diaries[i].date?.year == year && it.diaries[i].date?.month == month) {
                    val day = CalendarDay(it.diaries[i].date)
                    existContentList.add(day)
                }
            }
        })
        return existContentList
    }

    private fun existDayDiary() {
        viewModel.callGetDiary(emptyList())
        viewModel.diaryListLiveData.observe(viewLifecycleOwner, {
            for (i in 0..it?.diaries!!.size) {
                if (binding.calendarView.selectedDate.date.day == it.diaries[i].date?.day) {
                    binding.listEdit.text = "수정"
                }
            }
        })
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
            R.id.home -> {
                drawerBinding.drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}