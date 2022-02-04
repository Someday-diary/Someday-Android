package com.diary.someday.di

import com.diary.someday.model.db.SearchDatabase
import com.diary.someday.model.network.RetrofitClient
import com.diary.someday.model.network.dao.DiaryService
import com.diary.someday.model.network.dao.UserService
import com.diary.someday.model.network.util.Constants.API
import com.diary.someday.model.repository.*
import com.diary.someday.viewModel.*
import com.diary.someday.viewModel.repository.SearchRepository
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.create

val serviceModule = module {
    single {
        RetrofitClient.getClient(API.BASE_URL)?.create(UserService::class.java)
    }
    single {
        RetrofitClient.getClient(API.BASE_URL)?.create(DiaryService::class.java)
    }
}

val roomModule = module {
    single {
        SearchDatabase.getDatabase(androidContext())
    }
    single {
        get<SearchDatabase>().searchDao()
    }
    single {
        SearchRepository(get())
    }
    viewModel {
        SearchViewModel(get())
    }
}

val repositoryModule = module {
    single {
        FeedbackRepository(get())
    }
    single {
        DiaryRepository(get())
    }
    single {
        SignInRepository(get())
    }
    single {
        SignUpRepository(get())
    }
    single {
        MainRepository(get())
    }
}

val viewModelModule = module {
    viewModel {
        FeedbackViewModel(get())
    }
    viewModel {
        DiaryViewModel(get())
    }
    viewModel {
        SignInViewModel(get())
    }
    viewModel {
        SignUpViewModel(get())
    }
    viewModel {
        MainViewModel(get())
    }
}