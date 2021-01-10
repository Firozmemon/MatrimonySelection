package com.firozmemon.matrimonyselection.app

import android.app.Application
import com.firozmemon.matrimonyselection.di.appModule
import com.firozmemon.matrimonyselection.di.repoModule
import com.firozmemon.matrimonyselection.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MatrimonySelectionApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MatrimonySelectionApplication)
            modules(listOf(appModule, repoModule, viewModelModule))
        }
    }
}