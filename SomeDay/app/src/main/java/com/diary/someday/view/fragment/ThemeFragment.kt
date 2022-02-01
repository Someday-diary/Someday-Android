package com.yademos.someday.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.diary.someday.R
import com.diary.someday.di.application.Application
import com.diary.someday.databinding.FragmentThemeBinding

class ThemeFragment : Fragment() {
    lateinit var binding: FragmentThemeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThemeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        changedSelectedForColor(Application.themeSettingColor.getThemeTypeColor())

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.systemLayout.setOnClickListener {
            Application.themeSetting.themeType(1)
            Log.d("TAG", "themetype1: sharedpreference: ${Application.themeSetting.getThemeType()}")
            Log.d("TAG", "")
            changedSelectedForTheme(1)
        }

        binding.lightLayout.setOnClickListener {
            Application.themeSetting.themeType(2)
            Log.d("TAG", "themetype2: sharedpreference: ${Application.themeSetting.getThemeType()}")
            Log.d("TAG", "")
            changedSelectedForTheme(2)
        }


        binding.darkLayout.setOnClickListener {
            Application.themeSetting.themeType(3)
            Log.d("TAG", "themetype3: sharedpreference: ${Application.themeSetting.getThemeType()}")
            Log.d("TAG", "")
            changedSelectedForTheme(3)
        }

        binding.mintChocoLayout.setOnClickListener {
            Application.themeSettingColor.themeTypeColor(1)
            changedSelectedForColor(1)
        }

        binding.blueLemonaidLayout.setOnClickListener {
            Application.themeSettingColor.themeTypeColor(2)
            changedSelectedForColor(2)
        }

        binding.blueBerryaidLayout.setOnClickListener {
            Application.themeSettingColor.themeTypeColor(3)
            changedSelectedForColor(3)
        }

        binding.lemonaidLayout.setOnClickListener {
            Application.themeSettingColor.themeTypeColor(4)
            changedSelectedForColor(4)
        }

        binding.grapefruitLayout.setOnClickListener {
            Application.themeSettingColor.themeTypeColor(5)
            changedSelectedForColor(5)
        }



        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Log.d("TAG", "onStart: sharedpreference: ${Application.themeSetting.getThemeType()}")
        Log.d("TAG", "")
        when (Application.themeSetting.getThemeType()) {
            1 -> {
                changedSelectedForTheme(1, true)
            }
            2 -> {
                changedSelectedForTheme(2, true)
            }
            3 -> {
                changedSelectedForTheme(3, true)
            }
        }
        Log.d("TAG", "onCreateView: systembutton: ${binding.systemButton.isChecked}")
        Log.d("TAG", "onCreateView: lightbutton: ${binding.lightButton.isChecked}")
        Log.d("TAG", "onCreateView: darkButton: ${binding.darkButton.isChecked}")
//        if (Application.themeSetting.getThemeType()) {
//            changedSelected(1, true)
//        } else if (binding.lightButton.isChecked) {
//            changedSelected(2, true)
//        } else if (binding.darkButton.isChecked) {
//            changedSelected(3, true)
//        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun changedSelectedForTheme(number: Int, alreadyCheck: Boolean = false) {
        when (number) {
            1 -> {
                binding.systemButton.isChecked = true
                binding.lightButton.isChecked = false
                binding.darkButton.isChecked = false
                binding.systemText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.main_theme
                    )
                )
                binding.lightText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.light_textColor_drawer
                    )
                )
                binding.darkText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.light_textColor_drawer
                    )
                )
                binding.system.setImageDrawable(
                    ContextCompat.getDrawable(
                        activity as Context,
                        R.drawable.ic_system_selected
                    )
                )
                binding.light.setImageDrawable(
                    ContextCompat.getDrawable(
                        activity as Context,
                        R.drawable.ic_light
                    )
                )
                binding.dark.setImageDrawable(
                    ContextCompat.getDrawable(
                        activity as Context,
                        R.drawable.ic_dark
                    )
                )
                if (!alreadyCheck) changeMode(number)
            }
            2 -> {
                binding.systemButton.isChecked = false
                binding.lightButton.isChecked = true
                binding.darkButton.isChecked = false
                binding.systemText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.light_textColor_drawer
                    )
                )
                binding.lightText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.main_theme
                    )
                )
                binding.darkText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.light_textColor_drawer
                    )
                )
                binding.system.setImageDrawable(
                    ContextCompat.getDrawable(
                        activity as Context,
                        R.drawable.ic_system
                    )
                )
                binding.light.setImageDrawable(
                    ContextCompat.getDrawable(
                        activity as Context,
                        R.drawable.ic_light_selected
                    )
                )
                binding.dark.setImageDrawable(
                    ContextCompat.getDrawable(
                        activity as Context,
                        R.drawable.ic_dark
                    )
                )
                if (!alreadyCheck) changeMode(number)
            }
            3 -> {
                binding.systemButton.isChecked = false
                binding.lightButton.isChecked = false
                binding.darkButton.isChecked = true
                binding.systemText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.light_textColor_drawer
                    )
                )
                binding.lightText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.light_textColor_drawer
                    )
                )
                binding.darkText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.main_theme
                    )
                )
                binding.system.setImageDrawable(
                    ContextCompat.getDrawable(
                        activity as Context,
                        R.drawable.ic_system
                    )
                )
                binding.light.setImageDrawable(
                    ContextCompat.getDrawable(
                        activity as Context,
                        R.drawable.ic_light
                    )
                )
                binding.dark.setImageDrawable(
                    ContextCompat.getDrawable(
                        activity as Context,
                        R.drawable.ic_dark_selected
                    )
                )
                if (!alreadyCheck) changeMode(number)
            }
        }
    }

    private fun changedSelectedForColor(number: Int) {
        when (number) {
            1 -> {
                binding.mintChocoRadioButton.isChecked = true
                binding.blueLemonaidRadioButton.isChecked = false
                binding.blueBerryaidRadioButton.isChecked = false
                binding.lemonaidRadioButton.isChecked = false
                binding.grapefruitRadioButton.isChecked = false
                binding.mintChocoText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.green3
                    )
                )
                binding.blueLemonaidText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.light_textColor_drawer
                    )
                )
                binding.blueBerryaidText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.light_textColor_drawer
                    )
                )
                binding.lemonaidText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.light_textColor_drawer
                    )
                )
                binding.grapefruitText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.light_textColor_drawer
                    )
                )
            }
            2 -> {
                binding.mintChocoRadioButton.isChecked = false
                binding.blueLemonaidRadioButton.isChecked = true
                binding.blueBerryaidRadioButton.isChecked = false
                binding.lemonaidRadioButton.isChecked = false
                binding.grapefruitRadioButton.isChecked = false
                binding.mintChocoText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.light_textColor_drawer
                    )
                )
                binding.blueLemonaidText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.blue3
                    )
                )
                binding.blueBerryaidText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.light_textColor_drawer
                    )
                )
                binding.lemonaidText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.light_textColor_drawer
                    )
                )
                binding.grapefruitText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.light_textColor_drawer
                    )
                )
            }
            3 -> {
                binding.mintChocoRadioButton.isChecked = false
                binding.blueLemonaidRadioButton.isChecked = false
                binding.blueBerryaidRadioButton.isChecked = true
                binding.lemonaidRadioButton.isChecked = false
                binding.grapefruitRadioButton.isChecked = false
                binding.mintChocoText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.light_textColor_drawer
                    )
                )
                binding.blueLemonaidText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.light_textColor_drawer
                    )
                )
                binding.blueBerryaidText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.purple3
                    )
                )
                binding.lemonaidText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.light_textColor_drawer
                    )
                )
                binding.grapefruitText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.light_textColor_drawer
                    )
                )
            }
            4 -> {
                binding.mintChocoRadioButton.isChecked = false
                binding.blueLemonaidRadioButton.isChecked = false
                binding.blueBerryaidRadioButton.isChecked = false
                binding.lemonaidRadioButton.isChecked = true
                binding.grapefruitRadioButton.isChecked = false
                binding.mintChocoText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.light_textColor_drawer
                    )
                )
                binding.blueLemonaidText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.light_textColor_drawer
                    )
                )
                binding.blueBerryaidText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.light_textColor_drawer
                    )
                )
                binding.lemonaidText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.yellow3
                    )
                )
                binding.grapefruitText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.light_textColor_drawer
                    )
                )
            }
            5 -> {
                binding.mintChocoRadioButton.isChecked = false
                binding.blueLemonaidRadioButton.isChecked = false
                binding.blueBerryaidRadioButton.isChecked = false
                binding.lemonaidRadioButton.isChecked = false
                binding.grapefruitRadioButton.isChecked = true
                binding.mintChocoText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.light_textColor_drawer
                    )
                )
                binding.blueLemonaidText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.light_textColor_drawer
                    )
                )
                binding.blueBerryaidText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.light_textColor_drawer
                    )
                )
                binding.lemonaidText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.light_textColor_drawer
                    )
                )
                binding.grapefruitText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.red3
                    )
                )
            }
        }
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


}