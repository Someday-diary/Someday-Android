package com.diary.someday.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.text.style.ForegroundColorSpan
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.format.TitleFormatter
import com.diary.someday.R
import com.diary.someday.databinding.FragmentMainBinding
import java.text.SimpleDateFormat
import java.util.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.diary.someday.databinding.DrawerLayoutBinding
import com.diary.someday.decoration.CalendarDecorator
import com.diary.someday.viewModel.DiaryViewModel
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.diary.someday.application.Application

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
        if (Application.switchState.getSettingAll()) {
            Application.lockState.lockMode(2)
            findNavController().navigate(R.id.action_mainFragment_to_checkPwdFragment)
        }
        setHasOptionsMenu(true)
        initToolbar() // Toolbar 설정
        initCalendarView() // CalendarView 설정
        setCalendarViewTitle() // 이번 달
        bindingBottomSheetBehavior() // BottomSheet 설정
        binding.navigationView.itemIconTintList = null // 아이콘 원래 색깔로 설정하기 위함.
        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.themeSetting -> {
                    findNavController().navigate(R.id.action_mainFragment_to_themeFragment)
                }
                R.id.alarmSetting -> {
                    findNavController().navigate(R.id.action_mainFragment_to_alarmFragment)
                }
                R.id.lockSetting -> {
                    findNavController().navigate(R.id.action_mainFragment_to_lockFragment)
                }
            }
            false
        }


        changeMode(Application.themeSetting.getThemeType())


        return binding.root
    }

    override fun onStart() {
        super.onStart()
        changeModeColor(Application.themeSettingColor.getThemeTypeColor())
    }

    private fun changeMode(number: Int) {
        when (number) {
            1 -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
                // 안드로이드 10 미만
                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                }
            }
            2 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            3 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    private fun changeModeColor(number: Int) {
        when (number) {
            1 -> {
                binding.calendarLayout.calendarView.selectionColor = ContextCompat.getColor(
                    activity as Context,
                    R.color.green1
                )
                binding.calendarLayout.listTag.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.green3
                    )
                )
                binding.calendarLayout.bottomSheet.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.background_bottom_sheet_green
                )
                binding.calendarLayout.decorationImageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        activity as Context,
                        R.drawable.ic_calendar_decoration_green
                    )
                )
                binding.calendarLayout.listDate.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.circle_selected_green
                )
            }
            2 -> {
                binding.calendarLayout.calendarView.selectionColor = ContextCompat.getColor(
                    activity as Context,
                    R.color.blue1
                )
                binding.calendarLayout.listTag.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.blue3
                    )
                )
                binding.calendarLayout.bottomSheet.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.background_bottom_sheet_blue
                )
                binding.calendarLayout.decorationImageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        activity as Context,
                        R.drawable.ic_calendar_decoration_blue
                    )
                )
                binding.calendarLayout.listDate.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.circle_selected_blue
                )
            }
            3 -> {
                binding.calendarLayout.calendarView.selectionColor = ContextCompat.getColor(
                    activity as Context,
                    R.color.purple1
                )
                binding.calendarLayout.listTag.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.purple3
                    )
                )
                binding.calendarLayout.bottomSheet.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.background_bottom_sheet_purple
                )
                binding.calendarLayout.decorationImageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        activity as Context,
                        R.drawable.ic_calendar_decoration_purple
                    )
                )
                binding.calendarLayout.listDate.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.circle_selected_purple
                )
            }
            4 -> {
                binding.calendarLayout.calendarView.selectionColor = ContextCompat.getColor(
                    activity as Context,
                    R.color.yellow1
                )
                binding.calendarLayout.listTag.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.yellow3
                    )
                )
                binding.calendarLayout.bottomSheet.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.background_bottom_sheet_yellow
                )
                binding.calendarLayout.decorationImageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        activity as Context,
                        R.drawable.ic_calendar_decoration_yellow
                    )
                )
                binding.calendarLayout.listDate.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.circle_selected_yellow
                )
            }
            5 -> {
                binding.calendarLayout.calendarView.selectionColor = ContextCompat.getColor(
                    activity as Context,
                    R.color.red1
                )
                binding.calendarLayout.listTag.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.red3
                    )
                )
                binding.calendarLayout.bottomSheet.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.background_bottom_sheet_red
                )
                binding.calendarLayout.decorationImageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        activity as Context,
                        R.drawable.ic_calendar_decoration_red
                    )
                )
                binding.calendarLayout.listDate.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.circle_selected_red
                )
            }
        }
    }

    private fun initToolbar() {
        val activity = activity as AppCompatActivity

        activity.apply {
            setSupportActionBar(binding.calendarLayout.toolbar)
            supportActionBar?.apply {
                setDisplayShowTitleEnabled(false)
                setDisplayHomeAsUpEnabled(true)
                setHomeAsUpIndicator(R.drawable.ic_memu)
            }
        }
    }

    private fun initCalendarView() {
        val today: CalendarDay = CalendarDay.today()
        binding.calendarLayout.listDate.text = today.day.toString()


        binding.calendarLayout.calendarView.apply {
            state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2020, 12, 31))
                .setMaximumDate(CalendarDay.from(2099, 12, 26))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit()
            selectedDate = today

            existDayDiary(today)
            existMonthDiary(today.year, today.month) // 이번달 일기들 표시
            setOnMonthChangedListener { _, date -> // 그 다음달부터 달력 넘겼을때
                setCalendarViewTitle()
                existMonthDiary(date.year, date.month)
            }
            setOnDateChangedListener { _, date, _ ->
                binding.calendarLayout.listDate.text = date.day.toString()
                initContents()
                existDayDiary(date)
                binding.calendarLayout.listDate.text = date.day.toString()
            }
        }
    }

    private fun setCalendarViewTitle() {
        binding.calendarLayout.calendarView.setTitleFormatter(TitleFormatter {
            binding.calendarLayout.toolbarTitle.text = it.year.toString() + "년"
            val calendarViewFormat = SimpleDateFormat("M월")
            calendarViewFormat.format(it.date.time)
        })
    }

    private fun bindingBottomSheetBehavior() {
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.calendarLayout.bottomSheet)
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
                        R.drawable.background_bottom_sheet_green,
                        null
                    )
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })
        binding.calendarLayout.listEdit.setOnClickListener {
            val date = binding.calendarLayout.calendarView.selectedDate.date.time
            val year = binding.calendarLayout.calendarView.selectedDate.year.toString()
            val month = (binding.calendarLayout.calendarView.selectedDate.month + 1).toString()
            val day = binding.calendarLayout.calendarView.selectedDate.day.toString()
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToDiaryFragment(
                    year,
                    month,
                    day,
                    date,
                )
            )

        }
    }

    private fun existMonthDiary(year: Int, month: Int) {
        viewModel.callGetMonthDiary(year, month + 1)
        viewModel.monthDiaryLiveData.observe(viewLifecycleOwner, {
            if (it != null) {
                for (i in it!!.posts) {
                    val cDay = CalendarDay(i.date)
                    binding.calendarLayout.calendarView.addDecorators(
                        CalendarDecorator(
                            requireActivity(),
                            cDay
                        )
                    )
                }
            }
        })
    }

    private fun existDayDiary(date: CalendarDay) {
        viewModel.callGetDateDiary(date.year, date.month + 1, date.day)
        viewModel.dateDiaryLiveData.observe(viewLifecycleOwner, {
            initContents()
            if (it?.post == null) {
                binding.calendarLayout.listEdit.text = "일기 작성"
            } else {
                binding.calendarLayout.listEdit.text = "수정"
            }
            binding.calendarLayout.listContent.text = it?.post?.contents
            if (it?.post?.tags != null) {
                for (i in it.post.tags) {
                    binding.calendarLayout.listTag.append("#")
                    binding.calendarLayout.listTag.append(i.tag_name + " ")
                }
            }
        })
    }

    private fun initContents() {
        binding.calendarLayout.listContent.text = ""
        binding.calendarLayout.listTag.text = ""
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
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToSearchFragment())
                return super.onOptionsItemSelected(item)
            }
            android.R.id.home -> {
                binding.drawerlayout.openDrawer(GravityCompat.START)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("TAG", "\n\n\nonDestroyView: ")
        Application.lockState.delete()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG", "\n\n\nonDestroy: ")
        Application.lockState.delete()
    }
}