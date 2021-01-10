package com.firozmemon.matrimonyselection.di

import com.firozmemon.matrimonyselection.api.ApiClient
import com.firozmemon.matrimonyselection.app.MatrimonySelectionApplication
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val MAIN_SCHEDULER = "main_scheduler"
const val IO_SCHEDULER = "io_scheduler"
const val COMPUTATION_SCHEDULER = "computation_scheduler"

private fun provideUserApiService() = ApiClient.getUserApiService()

private fun provideMainScheduler(): Scheduler = AndroidSchedulers.mainThread()

private fun provideIOScheduler(): Scheduler = Schedulers.io()

private fun provideComputationScheduler(): Scheduler = Schedulers.computation()

private fun provideCompositeDisposable() = CompositeDisposable()

val appModule = module {
    single { androidApplication() as MatrimonySelectionApplication }
    single { provideUserApiService() }
    single(qualifier = named(MAIN_SCHEDULER)) { provideMainScheduler() }
    single(qualifier = named(IO_SCHEDULER)) { provideIOScheduler() }
    single(qualifier = named(COMPUTATION_SCHEDULER)) { provideComputationScheduler() }
    factory { provideCompositeDisposable() }
}