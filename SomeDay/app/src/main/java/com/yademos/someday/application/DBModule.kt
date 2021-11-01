package com.yademos.someday.application

import com.yademos.someday.db.AppDataBase
import com.yademos.someday.db.repository.DiaryRepository
import com.yademos.someday.viewModel.DiaryViewModel
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