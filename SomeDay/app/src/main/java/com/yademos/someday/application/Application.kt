package com.yademos.someday.application

import android.app.Application
import com.yademos.someday.db.DiaryDataBase
import com.yademos.someday.db.repository.DiaryRepository
import com.yademos.someday.viewModel.DiaryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@Application)
            modules(listOf(appModule))
        }
    }
}