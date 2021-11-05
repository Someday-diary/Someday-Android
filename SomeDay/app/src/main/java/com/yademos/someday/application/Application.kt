package com.yademos.someday.application

import android.app.Application
import com.yademos.someday.SharedPreferences.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class Application : Application() {

    companion object {
        lateinit var signUpCheck: SignUpCheck
        lateinit var themeSetting: ThemeSetting
        lateinit var themeSettingColor: ThemeSettingColor
        lateinit var lockNumber: LockNumber
        lateinit var lockNumberCheck: LockNumberCheck
        lateinit var switchState: SwitchState
    }

    override fun onCreate() {
        signUpCheck = SignUpCheck(applicationContext)
        themeSetting = ThemeSetting(applicationContext)
        themeSettingColor = ThemeSettingColor(applicationContext)
        lockNumber = LockNumber(applicationContext)
        lockNumberCheck = LockNumberCheck(applicationContext)
        switchState = SwitchState(applicationContext)
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@Application)
            modules(listOf(appModule))
        }
    }
}