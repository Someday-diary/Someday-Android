package com.diary.someday.view.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.diary.someday.R
import com.diary.someday.di.application.Application
import com.diary.someday.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeMode()
        changeModeColor(Application.themeSettingColor.getThemeTypeColor())

        startLoading()
    }

    private fun startLoading() {
        Handler().postDelayed(
            Runnable
            {
                finish()
            }, 2000
        )
    }

    private fun changeModeColor(number: Int) {
        when (number) {
            1 -> {
                binding.icon.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_icon_green
                    )
                )
                binding.splashText.setTextColor(ContextCompat.getColor(this, R.color.green3))
            }
            2 -> {
                binding.icon.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_icon_blue
                    )
                )
                binding.splashText.setTextColor(ContextCompat.getColor(this, R.color.blue3))
            }
            3 -> {
                binding.icon.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_icon_purple
                    )
                )
                binding.splashText.setTextColor(ContextCompat.getColor(this, R.color.purple3))
            }
            4 -> {
                binding.icon.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_icon_yellow
                    )
                )
                binding.splashText.setTextColor(ContextCompat.getColor(this, R.color.yellow3))
            }
            5 -> {
                binding.icon.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_icon_red
                    )
                )
                binding.splashText.setTextColor(ContextCompat.getColor(this, R.color.red3))
            }
        }
    }

    private fun changeMode() {
        when (Application.themeSetting.getThemeType()) {
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