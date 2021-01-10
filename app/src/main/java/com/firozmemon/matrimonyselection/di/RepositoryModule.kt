package com.firozmemon.matrimonyselection.di

import com.firozmemon.matrimonyselection.MainRepository
import com.firozmemon.matrimonyselection.db.MyLocalDatabase
import com.firozmemon.matrimonyselection.db.ProfilesDao
import org.koin.dsl.module

val repoModule = module {
    single<MainRepository> { MainRepository() }
    single<ProfilesDao> { MyLocalDatabase.getDatabase(get()).profilesDao() }
}
