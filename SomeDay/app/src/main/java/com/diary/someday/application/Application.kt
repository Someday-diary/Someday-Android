package com.diary.someday.application

import android.app.Application
import com.diary.someday.util.PreferenceUtils
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class Application : Application() {

    override fun onCreate() {
        PreferenceUtils.init(applicationContext)
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@Application)
            modules(listOf(DiaryModule))
        }
    }
}