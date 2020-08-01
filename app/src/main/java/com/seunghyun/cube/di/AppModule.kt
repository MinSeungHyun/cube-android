package com.seunghyun.cube.di

import com.seunghyun.cube.ui.function.FunctionViewModel
import com.seunghyun.cube.ui.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel() }
    viewModel { FunctionViewModel() }
}
