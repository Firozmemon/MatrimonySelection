package com.firozmemon.matrimonyselection

import android.content.Context
import android.net.ConnectivityManager
import com.firozmemon.matrimonyselection.api.UserApiService
import com.firozmemon.matrimonyselection.app.MatrimonySelectionApplication
import com.firozmemon.matrimonyselection.db.Profiles
import com.firozmemon.matrimonyselection.db.ProfilesDao
import com.firozmemon.matrimonyselection.di.IO_SCHEDULER
import com.firozmemon.matrimonyselection.di.MAIN_SCHEDULER
import io.reactivex.Scheduler
import io.reactivex.Single
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent

class MainRepository {

    private val TAG = "MainRepository"

    private val application: MatrimonySelectionApplication by KoinJavaComponent.inject(
        MatrimonySelectionApplication::class.java
    )
    private val userApiService: UserApiService by KoinJavaComponent.inject(UserApiService::class.java)
    private val profilesDao: ProfilesDao by KoinJavaComponent.inject(ProfilesDao::class.java)

    private val ioScheduler: Scheduler by KoinJavaComponent.inject(
        Scheduler::class.java,
        qualifier = named(IO_SCHEDULER)
    )

    private val mainScheduler: Scheduler by KoinJavaComponent.inject(
        Scheduler::class.java,
        qualifier = named(MAIN_SCHEDULER)
    )

    ///////////////////////////////////////////////////////////////////////////
    // Database
    ///////////////////////////////////////////////////////////////////////////
    fun getAllProfileList(): Single<List<Profiles>> = Single.create {
        val profileList = profilesDao.getAllProfiles()
        it.onSuccess(profileList!!)
    }

    fun insertNewProfilesList(profilesList: List<Profiles>): Single<Boolean> =
        Single.create {
            profilesList.forEach {
                insertNewProfile(it)
            }
            it.onSuccess(true)
        }

    private fun insertNewProfile(profiles: Profiles) {
        profilesDao.insert(profiles)
    }

    fun updateProfilesData(profiles: Profiles): Single<Boolean> = Single.create {
        profilesDao.updateProfiles(profiles)
        it.onSuccess(true)
    }

    ///////////////////////////////////////////////////////////////////////////
    // API calls
    ///////////////////////////////////////////////////////////////////////////
    fun getNewProfiles(): Single<List<Profiles>> = Single.create { e ->
        if (isNetworkConnected()) {
            userApiService.getRandomUserProfiles()
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe({ profileApiResponse ->
                    profileApiResponse?.let { response ->
                        val profilesList = ArrayList<Profiles>()
                        response.results.forEach { result ->
                            val fullName =
                                "${result.name.title} ${result.name.first} ${result.name.last}"
                            val age = result.dob.age
                            val imgUrl = result.picture.large
                            val address = "${result.location.city}, ${result.location.state}"
                            val currentProfile = Profiles(
                                name = fullName,
                                age = age,
                                imgUrl = imgUrl,
                                address = address
                            )
                            // println(currentProfile)
                            profilesList.add(currentProfile)
                        }

                        if (e.isDisposed.not()) e.onSuccess(profilesList)
                    } ?: run {
                        if (e.isDisposed.not()) e.onSuccess(ArrayList<Profiles>())
                    }

                }, { if (e.isDisposed.not()) e.onSuccess(ArrayList<Profiles>()) })
        } else {
            if (e.isDisposed.not()) e.onSuccess(ArrayList<Profiles>())
        }
    }

    private fun isNetworkConnected(): Boolean {
        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }

}
