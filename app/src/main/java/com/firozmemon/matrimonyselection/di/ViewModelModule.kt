package com.firozmemon.matrimonyselection.di

import com.firozmemon.matrimonyselection.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}