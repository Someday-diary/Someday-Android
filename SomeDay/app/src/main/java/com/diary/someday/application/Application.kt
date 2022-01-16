package com.diary.someday.application

import android.app.Application
import com.diary.someday.model.sharedpreferences.SignInCheck
import com.diary.someday.model.network.util.PreferenceUtils
import com.yademos.someday.SharedPreferences.*

class Application : Application() {

    companion object {
        lateinit var signUpCheck: SignUpCheck
        lateinit var themeSetting: ThemeSetting
        lateinit var themeSettingColor: ThemeSettingColor
        lateinit var lockNumber: LockNumber
        lateinit var switchState: SwitchState
        lateinit var signInCheck: SignInCheck
    }

    override fun onCreate() {
        signUpCheck = SignUpCheck(applicationContext)
        themeSetting = ThemeSetting(applicationContext)
        themeSettingColor = ThemeSettingColor(applicationContext)
        lockNumber = LockNumber(applicationContext)
        switchState = SwitchState(applicationContext)
        signInCheck = SignInCheck(applicationContext)
        PreferenceUtils.init(applicationContext)
        super.onCreate()
    }
}