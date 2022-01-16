package com.diary.someday.di.application

import android.app.Application
import com.diary.someday.di.repositoryModule
import com.diary.someday.di.serviceModule
import com.diary.someday.di.viewModelModule
import com.diary.someday.model.sharedpreferences.SignInCheck
import com.diary.someday.model.network.util.PreferenceUtils
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
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@Application)
            modules(listOf(serviceModule, repositoryModule, viewModelModule))
        }
        super.onCreate()
    }
}