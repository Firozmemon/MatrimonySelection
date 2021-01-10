package com.firozmemon.matrimonyselection

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.firozmemon.matrimonyselection.db.Profiles
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.java.KoinJavaComponent

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val mtldProfileListData: MutableLiveData<List<Profiles>> = MutableLiveData()
    val ldProfileListData: LiveData<List<Profiles>>
        get() = mtldProfileListData

    private val compositeDisposable: CompositeDisposable by KoinJavaComponent.inject(
        CompositeDisposable::class.java
    )
    private val bookingRepository: MainRepository by KoinJavaComponent.inject(MainRepository::class.java)

    fun getAllProfiles() {
        compositeDisposable.add(
            bookingRepository.getNewProfiles()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .flatMap { profileList -> bookingRepository.insertNewProfilesList(profileList) }
                .flatMap { bookingRepository.getAllProfileList() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { list -> mtldProfileListData.value = list }
        )
    }

    fun updateProfile(profiles: Profiles) {
        compositeDisposable.add(
            bookingRepository.updateProfilesData(profiles)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

}