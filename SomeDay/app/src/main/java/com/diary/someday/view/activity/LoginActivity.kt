package com.diary.someday.view.activity

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.diary.someday.R
import com.diary.someday.application.Application

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        startActivity(Intent(this, SplashActivity::class.java)) // Splash 실행
    }

    override fun getTheme(): Resources.Theme {
        var theme = super.getTheme()
        when (Application.themeSettingColor.getThemeTypeColor()) {
            1 -> {
                theme.applyStyle(R.style.GreenTheme, true)
            }
            2 -> {
                theme.applyStyle(R.style.BlueTheme, true)
            }
            3 -> {
                theme.applyStyle(R.style.PurpleTheme, true)
            }
            4 -> {
                theme.applyStyle(R.style.YellowTheme, true)
            }
            5 -> {
                theme.applyStyle(R.style.RedTheme, true)
            }
        }
        return theme
    }
}