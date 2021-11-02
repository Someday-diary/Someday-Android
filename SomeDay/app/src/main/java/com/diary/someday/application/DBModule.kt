package com.diary.someday.application

import com.diary.someday.db.AppDataBase
import com.diary.someday.db.repository.DiaryRepository
import com.diary.someday.viewModel.DiaryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val DiaryModule = module {
    val applicationScope = CoroutineScope(SupervisorJob())

    single {
        AppDataBase.getDiaryDb(androidApplication(), applicationScope)
    }
    single {
        DiaryRepository(get())
    }
    single {
        get<AppDataBase>().diaryDao()
    }
    viewModel {
        DiaryViewModel(get())
    }
}