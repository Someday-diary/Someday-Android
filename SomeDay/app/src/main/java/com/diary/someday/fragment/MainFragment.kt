package com.diary.someday.fragment

import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.text.style.ForegroundColorSpan
import android.view.*
import android.widget.Toast
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
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.diary.someday.Constants.PWD_TYPE
import com.diary.someday.databinding.DrawerLayoutBinding
import com.diary.someday.decoration.CalendarDecorator
import com.diary.someday.viewModel.DiaryViewModel
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.diary.someday.application.Application
import java.util.concurrent.Executor

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var drawerBinding: DrawerLayoutBinding
    private val viewModel: DiaryViewModel by viewModels()

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Application.switchState.getSettingAll()) {
            if (Application.switchState.getBioSwitch()) {
                biometric()
            } else {
                Application.lockNumber.addType(PWD_TYPE.CHECK_LOCK_MAIN)
                findNavController().navigate(R.id.action_mainFragment_to_editPwdFragment)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        drawerBinding = DrawerLayoutBinding.inflate(inflater, container, false)

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


//        changeMode(Application.themeSetting.getThemeType())


        return binding.root
    }


    override fun onResume() {
        super.onResume()
        Log.d("TAG", "onResume: ")
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

    private fun biometric() {
        executor = ContextCompat.getMainExecutor(activity as Context)
        biometricPrompt = BiometricPrompt(
            requireActivity(), executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(activity as Context, "성공했어요.", Toast.LENGTH_SHORT).show()
                    Application.lockNumber.done()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("생체 정보로 인증해주세요")
            .setNegativeButtonText("비밀번호로 인증")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_WEAK)
            .build()

        biometricPrompt.authenticate(promptInfo)
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
                binding.calendarLayout.view.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.background_bottom_sheet_handle_green
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
                binding.calendarLayout.calendarView.selectionColor = ContextCompat.getColor(
                    activity as Context,
                    R.color.green1
                )
                binding.navigationView.menu.clear()
                binding.navigationView.inflateMenu(R.menu.navi_menu_green)
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
                binding.calendarLayout.view.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.background_bottom_sheet_handle_blue
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
                binding.calendarLayout.calendarView.selectionColor = ContextCompat.getColor(
                    activity as Context,
                    R.color.blue1
                )
                binding.navigationView.menu.clear()
                binding.navigationView.inflateMenu(R.menu.navi_menu_blue)
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
                binding.calendarLayout.view.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.background_bottom_sheet_handle_purple
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
                binding.calendarLayout.calendarView.selectionColor = ContextCompat.getColor(
                    activity as Context,
                    R.color.purple1
                )
                binding.navigationView.menu.clear()
                binding.navigationView.inflateMenu(R.menu.navi_menu_purple)
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
                binding.calendarLayout.view.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.background_bottom_sheet_handle_yellow
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
                binding.calendarLayout.calendarView.selectionColor = ContextCompat.getColor(
                    activity as Context,
                    R.color.yellow1
                )
                binding.navigationView.menu.clear()
                binding.navigationView.inflateMenu(R.menu.navi_menu_yellow)
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
                binding.calendarLayout.view.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.background_bottom_sheet_handle_red
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
                binding.calendarLayout.calendarView.selectionColor = ContextCompat.getColor(
                    activity as Context,
                    R.color.red1
                )
                binding.navigationView.menu.clear()
                binding.navigationView.inflateMenu(R.menu.navi_menu_red)
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
                    when (Application.themeSettingColor.getThemeTypeColor()) {
                        1 -> {
                            bottomSheet.background = ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.background_bottom_sheet_green,
                                null
                            )
                        }
                        2 -> {
                            bottomSheet.background = ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.background_bottom_sheet_blue,
                                null
                            )
                        }
                        3 -> {
                            bottomSheet.background = ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.background_bottom_sheet_purple,
                                null
                            )
                        }
                        4 -> {
                            bottomSheet.background = ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.background_bottom_sheet_yellow,
                                null
                            )
                        }
                        5 -> {
                            bottomSheet.background = ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.background_bottom_sheet_red,
                                null
                            )
                        }
                    }

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
                binding.calendarLayout.listTag.text = getString(R.string.not_exist_tag)
                binding.calendarLayout.listContent.text = getString(R.string.not_exist_diary)
                return@observe
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
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG", "\n\n\nonDestroy: ")
        Application.lockNumber.delete()
    }
}