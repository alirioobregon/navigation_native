package com.example.test1.core.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.test1.presentation.LoginViewModel
import org.koin.dsl.module


val viewModelModule = module {
    factory { LoginViewModel(get()) }

}