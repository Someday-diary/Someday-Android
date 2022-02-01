package com.diary.someday.application.module

import com.diary.someday.model.db.SearchDatabase
import com.diary.someday.viewModel.SearchViewModel
import com.diary.someday.viewModel.repository.SearchRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.compat.ScopeCompat.viewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        SearchDatabase.getDatabase(androidApplication())
    }
    single {
        SearchRepository(get())
    }
    single {
        get<SearchDatabase>().searchDao()
    }
    viewModel {
        SearchViewModel(get())
    }
}