package com.firozmemon.matrimonyselection.api

import com.firozmemon.matrimonyselection.model.ProfileApiResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApiService {

    @GET("api/?results=10")
    fun getRandomUserProfiles(): Single<ProfileApiResponse>

}